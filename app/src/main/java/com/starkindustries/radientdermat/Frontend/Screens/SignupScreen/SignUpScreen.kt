package com.starkindustries.radientdermat.Frontend.Screens.SignupScreen

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.starkindustries.radientdermat.Backend.Api.AuthApi
import com.starkindustries.radientdermat.Backend.Instance.AuthApiInstance
import com.starkindustries.radientdermat.Keys.Keys
import com.starkindustries.radientdermat.Frontend.Routes.Routes
import com.starkindustries.radientdermat.Frontend.Screens.Compose.AuthenticationLogoTextCompose
import com.starkindustries.radientdermat.Frontend.Screens.Compose.GalleryPickerCompose
import com.starkindustries.radientdermat.Frontend.Screens.Compose.SwitchScreenCompose
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.Patient
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.PatientsResponse
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.SignupRequest
import com.starkindustries.radientdermat.R
import com.starkindustries.radientdermat.ui.theme.BlueBackground
import com.starkindustries.radientdermat.ui.theme.purpleGradient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

fun uploadProfilePicture(context: Context, imageUri: Uri, username: String, onResult: (Patient?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val file: File = getFileFromUri(imageUri,context) ?: throw Exception("File conversion failed")

            val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val multipartBody = MultipartBody.Part.createFormData("image", file.name, requestBody)

            val response = AuthApiInstance.api.uploadProfilePic(username,multipartBody)

            if (response.isSuccessful) {
                Log.d("PROFILE_PIC_UPLOAD", "Upload Successful: ${response.body()}")

                onResult(response.body()) // Pass success responsea
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("PROFILE_PIC_UPLOAD", "Upload Failed: HTTP ${response.code()} - $errorBody")
                onResult(null) // Handle failure
            }
        } catch (e: HttpException) {
            Log.e("PROFILE_PIC_UPLOAD", "HttpException: ${e.message}")
            onResult(null)
        } catch (e: IOException) {
            Log.e("PROFILE_PIC_UPLOAD", "IOException: ${e.message}")
            onResult(null)
        } catch (e: Exception) {
            Log.e("PROFILE_PIC_UPLOAD", "Exception: ${e.message}")
            onResult(null)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController){

    var name by remember{
        mutableStateOf("")
    }

    var username by remember{
        mutableStateOf("")
    }

    var password by remember{
        mutableStateOf("")
    }

    var phoneNo by remember{
        mutableStateOf("")
    }
    
    var email by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var profilePicUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var coroutineScope = rememberCoroutineScope()

    var context = LocalContext.current.applicationContext
    var sharedPrefrences = context.getSharedPreferences(Keys.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    var editor = sharedPrefrences.edit()

    var isEmailValid by remember{
        mutableStateOf(true)
    }

    val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]{2,3}$")

    var isNameValid by remember { mutableStateOf(true) }

    var isUsernameValid by remember{
        mutableStateOf(true)
    }

    var isPasswordValid by remember {
        mutableStateOf(true)
    }

    val passwordPattern = Regex("^(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+$).{9,}$")

    Column(verticalArrangement = Arrangement.Center
    , modifier = Modifier
            .fillMaxSize()) {

        Text(text = "Signup"
            , fontSize = 35.sp
            , fontWeight = FontWeight.Bold
            , modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
            , textAlign = TextAlign.Center)


        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            , contentAlignment = Alignment.Center){
            Column {
                GalleryPickerCompose { uri->
                    profilePicUri=uri
                    Log.d("URI",profilePicUri.toString())
                }

            }
        }


        Box(modifier = Modifier
            .fillMaxSize()){
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp)
                    .matchParentSize()// Increased height of the Canvas
            ) {
                val path = Path().apply {
                    // Start from the top-left corner
                    moveTo(0f, size.height * 0.145f)

                    // Create a large upward curve
                    quadraticBezierTo(
                        size.width * 0.4f,
                        size.height * 0.0006f,  // Higher control point for outward curve
                        size.width,
                        size.height * 0.1f
                    )

                    // Draw the rest of the shape down to the bottom
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(
                    path = path,
                    brush = purpleGradient
                )
            }

            Column(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)) {

                Spacer(modifier = Modifier
                    .height(130.dp))

                TextField(value = name
                    , onValueChange = {
                        name=it
                        isNameValid=it.isNotEmpty()
                    }
                    , shape = CircleShape

                    , label = {
                        if(isNameValid){
                            Text(text = "Name"
                                , fontSize = 18.sp
                                , style = MaterialTheme.typography.titleMedium
                                , color = Color.Black
                            )
                        }else{
                            Text(text = "Name should not be empty!!"
                                , fontSize = 18.sp
                                , style = MaterialTheme.typography.titleMedium
                                , color = Color.Red
                            )
                        }

                    }
                    , textStyle = TextStyle(fontSize = 18.sp)
                    , modifier = Modifier
                        .fillMaxWidth()
                , colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                        , unfocusedIndicatorColor = Color.Transparent
                        , focusedIndicatorColor = Color.Transparent
                        ,unfocusedTextColor = Color.Black
                        , focusedTextColor = Color.Black
                ))

                Spacer(modifier = Modifier
                    .height(15.dp))
                TextField(value = email
                    , onValueChange = {
                        email=it
                        isEmailValid = email.matches(emailPattern)
                    }
                    , shape = CircleShape
                    , isError = !isEmailValid
                    , label = {
                        if(!isEmailValid){
                            Text(text = "Invalid Email Format"
                                , fontSize = 18.sp
                                , style = MaterialTheme.typography.titleMedium
                                , color = Color.Red
                            )
                    }else{
                            Text(text = "Email"
                                , fontSize = 18.sp
                                , style = MaterialTheme.typography.titleMedium
                                , color = Color.Black
                            )
                        }

                    }
                    , textStyle = TextStyle(fontSize = 18.sp)
                    , modifier = Modifier
                        .fillMaxWidth()
                    , colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White
                        , unfocusedIndicatorColor = Color.Transparent
                        , focusedIndicatorColor = Color.Transparent
                        ,unfocusedTextColor = Color.Black
                        , focusedTextColor = Color.Black
                    ))


                Spacer(modifier = Modifier
                    .height(15.dp))

                TextField(value = username
                    , onValueChange = {
                        username=it
                        isUsernameValid=it.isNotEmpty()
                    }
                    , shape = CircleShape
                    , label = {
                        if(isUsernameValid){
                            Text(text = "Username"
                                , fontSize = 18.sp
                                , style = MaterialTheme.typography.titleMedium
                                , color = Color.Black
                            )
                        }else{
                            Text(text = "Enter proper Username!!"
                                , fontSize = 18.sp
                                , style = MaterialTheme.typography.titleMedium
                                , color = Color.Red
                            )
                        }
                    }
                    , textStyle = TextStyle(fontSize = 18.sp)
                    , modifier = Modifier
                        .fillMaxWidth()
                    , colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White
                        , unfocusedIndicatorColor = Color.Transparent
                        , focusedIndicatorColor = Color.Transparent
                        ,unfocusedTextColor = Color.Black
                        , focusedTextColor = Color.Black
                    ))

                Spacer(modifier = Modifier
                    .height(15.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it
                        isPasswordValid = passwordPattern.matches(it)
                                    },
                    shape = CircleShape,
                    label = {
                        if(!isPasswordValid){

                            Text(text = "Password must be >8 chars, 1 uppercase, 1 special char!!"
                                , fontSize = 18.sp
                                , style = MaterialTheme.typography.titleMedium
                                , color = Color.Red
                            )


                        }else{
                            Text(text = "Password"
                                , fontSize = 18.sp
                                , style = MaterialTheme.typography.titleMedium
                                , color = Color.Black
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = if (passwordVisible) painterResource(id =R.drawable.visibility_on_two ) else painterResource(id =R.drawable.visibility_off)
                                , contentDescription = if (passwordVisible) "Hide password" else "Show password"
                            )
                        }
                    }
                    , textStyle = TextStyle(fontSize = 18.sp)
                    , modifier = Modifier
                        .fillMaxWidth(  )
                    , colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White
                        , unfocusedIndicatorColor = Color.Transparent
                        , focusedIndicatorColor = Color.Transparent
                        ,unfocusedTextColor = Color.Black
                        , focusedTextColor = Color.Black
                    ))


                Spacer(modifier =Modifier
                    .height(20.dp))

                Box(contentAlignment = Alignment.Center
                , modifier = Modifier
                        .fillMaxWidth()) {

                    Button(onClick = {

                        if(profilePicUri!=null){
                            coroutineScope.launch {

                                val patients = Patient(
                                    name = name.toString().trim(),
                                    username = username.toString().trim(),
                                    email = email.toString().trim(),
                                    password = password.toString().trim(),
                                    profilePicUrl = "",
                                    medicalHistory = ""
                                )

//// Convert Patient object to JSON string
//                            val jsonString = Gson().toJson(patients)
//                            val userRequestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull()) // Ensure correct content type
//
//// Convert URI to file (Scoped Storage Fix)
//                            val file = profilePicUri?.let { getFileFromUri(it, context) }
//                            if (file == null) {
//                                Log.e("Signup", "Error: Unable to get file from URI")
//                                return@launch
//                            }
//
//                            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//                            val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)
//
//// Make API call
//                            val call = AuthApiInstance.api.signup(imagePart, userRequestBody)
//                            call.enqueue(object : Callback<PatientsResponse> {
//                                override fun onResponse(call: Call<PatientsResponse>, response: Response<PatientsResponse>) {
//                                    if (response.isSuccessful) {
//                                        Log.d("Signup", "Success: ${response.body()}")
//                                    } else {
//                                        Log.e("Signup", "Failure: ${response.errorBody()?.string()}")
//                                    }
//                                }
//
//                                override fun onFailure(call: Call<PatientsResponse>, t: Throwable) {
//                                    Log.e("Signup", "Error: ${t.message}")
//                                }
//                            })

                                try{
                                    var response = AuthApiInstance.api.signup(patients)

                                    if(response.isSuccessful){
                                        Log.d("API_RESPONSE",response.body().toString())

                                        if(profilePicUri!=null){
                                            Log.d("PROFILE_PIC_URI",profilePicUri.toString())
                                            uploadProfilePicture(context = context, imageUri = profilePicUri!!,username=username.toString().trim()){ patients->

                                                if (patients != null) {
                                                    editor.putString(Keys.PROFILE_PIC_URL,patients.profilePicUrl)
                                                    editor.putString(Keys.USERNAME,patients.username)
                                                    editor.putString(Keys.NAME,patients.name)
                                                    editor.putString(Keys.EMAIL,patients.email)
                                                    editor.putString(Keys.PASSWORD,password)
                                                    editor.commit()
                                                    editor.apply()
                                                }
                                                Handler(Looper.getMainLooper()).post {
                                                    navController.navigate(Routes.PATIENT_DASHBOARD_SCREEN_ROUTE.route){
                                                        popUpTo(0){
                                                            inclusive=true
                                                        }
                                                    }


                                                }
                                            }
                                        }else
                                            Log.d("PROFILE_PIC_NULL","profile pic uri is null")

                                    }else
                                        Log.d("API_ERROR",response.errorBody().toString())

                                }catch (e:Exception){
                                    e.localizedMessage?.let { Log.d("API_EXPECTION", it.toString()) }
                                }



                            }
                        }else
                            Toast.makeText(context, "Select a Profile pic first!!", Toast.LENGTH_SHORT).show()





                    }
                        , colors = ButtonDefaults.buttonColors(
                            containerColor = BlueBackground
                            , contentColor = Color.White
                        )) {
                        Text(text = "Sign Up"
                            , fontSize = 18.sp
                            , fontWeight = FontWeight.W500)
                    }
                }

                Spacer(modifier = Modifier
                    .height(80.dp))

                    SwitchScreenCompose(text1 = "Already have an account,", text2 = "login", navController = navController, route = Routes.LOGIN_SCREEN_ROUTE.route)


            }
        }

    }
}

fun getFileFromUri(uri: Uri, context: Context): File? {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null
    val file = File(context.cacheDir, "temp_profile.jpg")
    file.outputStream().use { output -> inputStream.copyTo(output) }
    return file
}


fun getRealPathFromURI(uri: Uri, contentResolver: ContentResolver): String? {
    var realPath: String? = null
    val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val columnIndex = it.getColumnIndex(MediaStore.Images.Media.DATA)
            if (columnIndex != -1) {
                realPath = it.getString(columnIndex)
            }
        }
    }
    return realPath
}


fun uriToFile(context: Context, uri: Uri): File? {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)

    inputStream?.use { input ->
        FileOutputStream(tempFile).use { output ->
            input.copyTo(output)
        }
    }

    return if (tempFile.exists()) tempFile else null
}

fun prepareFilePart(context: Context, uri: Uri, partName: String): MultipartBody.Part {

    val contentResolver: ContentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)

    inputStream?.use { input ->
        FileOutputStream(tempFile).use { output ->
            input.copyTo(output)
        }
    }

    val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), tempFile)
    return MultipartBody.Part.createFormData(partName, tempFile.name, requestFile)
}

fun createPatientRequestBody(patients: Patient): RequestBody {
    val json = Gson().toJson(patients)
    return RequestBody.create("application/json".toMediaTypeOrNull(), json)
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SplashScreenPreview(){
    SignUpScreen(rememberNavController())
}