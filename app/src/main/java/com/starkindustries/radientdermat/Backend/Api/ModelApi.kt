package com.starkindustries.radientdermat.Backend.Api

import com.starkindustries.radientdermat.Backend.Data.ModelResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ModelApi {

    @Multipart
    @POST("predict")
    suspend fun modelResponse(@Part image:MultipartBody.Part):Response<ModelResponse>

}