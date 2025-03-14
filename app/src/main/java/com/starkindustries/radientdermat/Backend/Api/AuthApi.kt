package com.starkindustries.radientdermat.Backend.Api

import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.LoginRequest
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.Patient
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.PatientsResponse
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.SignupRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface AuthApi {

//    @Multipart
//    @POST("auth/signup")
//    fun signup(
//        @Part image: MultipartBody.Part,  // Image file
//        @Part("user") userData: RequestBody // JSON data as a string
//    ): Call<PatientsResponse>

    @POST("auth/signup")
    suspend fun signup(@Body patient:Patient):Response<Patient>

    @Multipart
    @PUT("auth/add-profile-pic/{username}")
    suspend fun uploadProfilePic(@Path("username") username:String, @Part image: MultipartBody.Part) :Response<Patient>

    @POST("auth/login")
    suspend fun login(@Body loginRequest:LoginRequest):Response<String>

    @GET("auth/greetings")
    suspend fun greetings():Response<String>
}