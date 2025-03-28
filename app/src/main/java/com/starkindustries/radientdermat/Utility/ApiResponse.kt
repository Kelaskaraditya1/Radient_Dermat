package com.starkindustries.radientdermat.Utility

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.java.GenerativeModelFutures
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.starkindustries.radientdermat.Keys.Keys
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Fragments.uriToBitmap

fun getSymptoms(diseaseName:String,context:Context):String{

    val mainExecutor = ContextCompat.getMainExecutor(context)

    var textResponse=""

    val generativeModel = GenerativeModel(
        modelName = Keys.MODEL_NAME,
        apiKey = Keys.API_KEY
    )

    val model = GenerativeModelFutures.from(generativeModel)


    val content = Content.Builder()
        .text("Give Symptoms of ${diseaseName} this disease in 1-2 paragraph there should be no mention of ai in the response")
        .build()

    val response: ListenableFuture<GenerateContentResponse>? = content?.let {
        model.generateContent(
            it
        )
    }

    Futures.addCallback(response, object :
        FutureCallback<GenerateContentResponse> {
        override fun onSuccess(result: GenerateContentResponse?) {
            result?.let {
                textResponse = it.text ?: "No response received"
                Log.d("GeminiResponse", "Success: $textResponse")
            }
        }

        override fun onFailure(t: Throwable) {
            Log.e("GeminiResponse", "Error: ${t.message}", t)

        }
    }, mainExecutor)

    return textResponse
}