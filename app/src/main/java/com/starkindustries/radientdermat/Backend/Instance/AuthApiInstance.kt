package com.starkindustries.radientdermat.Backend.Instance

import android.util.Log
import com.google.gson.GsonBuilder
import com.starkindustries.radientdermat.Backend.Api.AuthApi
import com.starkindustries.radientdermat.Frontend.Keys.Keys
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AuthApiInstance {
    val api: AuthApi by lazy {

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Custom CookieJar to handle session cookies (JSESSIONID)
        val cookieJar = object : CookieJar {
            private val cookieStore = HashMap<String, List<Cookie>>()

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                for (cookie in cookies) {
                    if (cookie.name == "JSESSIONID") {
                        cookieStore[url.host] = cookies
                        Log.d("SESSION", "Saved JSESSIONID: ${cookie.value}")
                    }
                }
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                return cookieStore[url.host] ?: emptyList()
            }
        }

        // Modified OkHttpClient with logging for debugging cookies
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)

                // Debugging Logs
                Log.d("SESSION", "Request URL: ${request.url}")
                Log.d("SESSION", "Request Headers: ${request.headers}")
                Log.d("SESSION", "Response Headers: ${response.headers}")
                Log.d("SESSION", "Cookies Sent: ${request.header("Cookie")}")
                Log.d("SESSION", "Cookies Received: ${response.headers("Set-Cookie")}")

                response
            }
            .cookieJar(cookieJar) // Attach CookieJar for session management
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder().setLenient().create()

        Retrofit.Builder()
            .baseUrl(Keys.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(AuthApi::class.java)
    }
}

