package com.starkindustries.radientdermat.Backend.Api

import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.LoginRequest
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.Patient
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.PatientsResponse
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.SignupRequest
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.UpdatePassword
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.UpdatedPatient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
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

    @PUT("auth/update-profile/{username}")
    suspend fun updatePatientProfile(@Path("username") username: String,@Body updatePatient: UpdatedPatient,@Header("Authorization") jwtToken:String):Response<Patient>

    @Multipart
    @PUT("/auth/update-profile-pic/{username}")
    suspend fun updateProfilePic(@Path("username") username: String,@Part image: MultipartBody.Part,@Header("Authorization") jwtToken:String):Response<Patient>

    @PUT("auth/update-password/{username}")
    suspend fun updatePassword(@Body updatePassword: UpdatePassword,@Header("Authorization") jwtToken: String,@Path("username") username: String):Response<Patient>

    @GET("auth/get-patient/{username}")
    suspend fun getPatient(@Path("username") username: String,@Header("Authorization") jwtToken: String):Response<Patient>

    @GET("auth/send-email/{email}")
    suspend fun sendEmail(@Path("email") email:String):Response<ResponseBody>

    @GET("auth/verify-email/{otp}")
    suspend fun verifyEmail(@Path("otp") otp:Int):Response<ResponseBody>


}