package com.starkindustries.radientdermat.Frontend.Screens.Patient

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.starkindustries.radientdermat.Backend.Instance.ModelInstance
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

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

fun uriToFile(context: Context, uri: Uri): File {
    val fileName = getFileName(context, uri)
    val file = File(context.cacheDir, fileName)

    try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return file
}

// Get Original File Name from URI
fun getFileName(context: Context, uri: Uri): String {
    var name = "temp_image.jpg"
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                name = it.getString(nameIndex)
            }
        }
    }
    return name
}



fun saveBitmapAsJpeg(bitmap: Bitmap, file: File) {
    try {
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream) // 100 = max quality
        outputStream.flush()
        outputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}



fun convertDrawableToMultipart(context: Context, drawableId: Int): MultipartBody.Part {
    val drawable = ContextCompat.getDrawable(context, drawableId) ?: return MultipartBody.Part.createFormData("file", "")
    val bitmap = (drawable as BitmapDrawable).bitmap

    // Save bitmap to a temporary file
    val file = File(context.cacheDir, "image.jpg")
    val outputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.flush()
    outputStream.close()

    // Convert file to MultipartBody.Part
    val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("file", file.name, requestFile)
}


@Composable
fun ModelPrediction() {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        try {
            val jpegImage = convertDrawableToMultipart(context, R.drawable.shingles)
            val response = ModelInstance.api.modelResponse(jpegImage)

            if (response.isSuccessful) {
                val modelResponse = response.body()
                modelResponse?.let {
                    Log.d("MODEL_RESPONSE", it.prediction)
                }
            } else {
                Log.d("MODEL_ERROR", "Error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("MODEL_PREDICTION_EXCEPTION", "Exception: ${e.localizedMessage}", e)
        }

    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Preview(){
DiseasePredictionTest()
}

