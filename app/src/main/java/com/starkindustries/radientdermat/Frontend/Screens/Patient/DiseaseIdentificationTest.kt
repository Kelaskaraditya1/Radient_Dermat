package com.starkindustries.radientdermat.Frontend.Screens.Patient

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.java.GenerativeModelFutures
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.starkindustries.radientdermat.R
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

@Composable
fun DiseasePredictionTest(){

    val context = LocalContext.current
    val mainExecutor = ContextCompat.getMainExecutor(context)

    Box(modifier = Modifier
        .fillMaxSize()
    , contentAlignment = Alignment.Center){
        Button(onClick = {
            val generativeModel = GenerativeModel(
                "gemini-1.5-pro",
                "AIzaSyC-UFan8En1abi5CWwzEA5BWhIeN91fU7k"
            )

            val model = GenerativeModelFutures.from(generativeModel)

            val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.shingles)

            val content = Content.Builder()
                .text("What is the name of the disease?, give only one word answer that is the name and no other word")
                .image(bitmap)
                .build()

            val response: ListenableFuture<GenerateContentResponse> = model.generateContent(content)

            Futures.addCallback(response, object : FutureCallback<GenerateContentResponse> {
                override fun onSuccess(result: GenerateContentResponse?) {
                    result?.let {
                        val textResponse = it.text ?: "No response received"
                        Log.d("GeminiResponse", "Success: $textResponse")
                    }
                }

                override fun onFailure(t: Throwable) {
                    Log.e("GeminiResponse", "Error: ${t.message}", t)

                }
            }, mainExecutor)
        }) {
            Text(text = "Disease name")
        }
    }


}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Preview(){
DiseasePredictionTest()
}

