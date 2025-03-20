package com.starkindustries.radientdermat.Backend.Api

import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.PatientTest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface PatientTestApi {

    @POST("test/add-to-history/{username}")
    suspend fun addToHistory(@Body patientTest: PatientTest,@Path("username") username:String,@Header("Authorization") jwtToken:String):Response<PatientTest>

    @Multipart
    @PUT("test/add-test-image/{testId}")
    suspend fun addImageToTest(@Path("testId") testId:Int,@Header("Authorization") jwtToken: String,@Part image:MultipartBody.Part):Response<PatientTest>

    @GET("test/get-tests/{username}")
    suspend fun getTests(@Path("username") username:String,@Header("Authorization") jwtToken: String):Response<List<PatientTest>>

    @DELETE("test/delete-test/{testId}")
    suspend fun deleteTest(@Path("testId") testId:Int,@Header("Authorization") jwtToken: String):Response<Unit>
}