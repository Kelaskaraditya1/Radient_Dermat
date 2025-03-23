package com.starkindustries.radientdermat.Frontend.Screens.Patient.Fragments

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.PhotoCameraBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.starkindustries.radientdermat.Backend.Instance.AuthApiInstance
import com.starkindustries.radientdermat.Frontend.Keys.Keys
import com.starkindustries.radientdermat.Frontend.Routes.Routes
import com.starkindustries.radientdermat.Frontend.Screens.Compose.CircularImageProfile
import com.starkindustries.radientdermat.Frontend.Screens.Compose.GalleryPickerCompose
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.MedicalHistory
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.Patient
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.UpdatePassword
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.UpdatedPatient
import com.starkindustries.radientdermat.Frontend.Screens.SignupScreen.getFileFromUri
import com.starkindustries.radientdermat.R
import com.starkindustries.radientdermat.Utility.Utility
import com.starkindustries.radientdermat.ui.theme.BlueBackground
import com.starkindustries.radientdermat.ui.theme.Purple40
import com.starkindustries.radientdermat.ui.theme.Purple80
import com.starkindustries.radientdermat.ui.theme.blueGradientBrush
import com.starkindustries.radientdermat.ui.theme.brightGreenGradient
import com.starkindustries.radientdermat.ui.theme.cardBlueBackground
import com.starkindustries.radientdermat.ui.theme.multiShadeYellowGradient
import com.starkindustries.radientdermat.ui.theme.orangeGradient
import com.starkindustries.radientdermat.ui.theme.purpleGradient
import com.starkindustries.radientdermat.ui.theme.redGradientBrush
import com.starkindustries.radientdermat.ui.theme.whiteBrush
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

fun uploadProfilePicture(context: Context, imageUri: Uri, username: String, jwtToken:String,onResult: (Patient?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val file: File = getFileFromUri(imageUri,context) ?: throw Exception("File conversion failed")

            val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val multipartBody = MultipartBody.Part.createFormData("image", file.name, requestBody)

            val response = AuthApiInstance.api.updateProfilePic(username = username,multipartBody, jwtToken = "Bearer ${jwtToken.trim()}")

            if (response.isSuccessful) {
                Log.d("PROFILE_PIC_UPLOAD", "Upload Successful: ${response.body()}")

                onResult(response.body()) // Pass success response
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

@Composable
fun ProfileFragment(navController:NavController,pagerState: PagerState){

    val scrollState = rememberScrollState()

    val context = LocalContext.current.applicationContext

    var coRoutineScope = rememberCoroutineScope()

    val sharedPrefrences = context.getSharedPreferences(Keys.SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)

    val editor = sharedPrefrences.edit()

    var editProfileDialogState by remember{
        mutableStateOf(false)
    }

    var updatePasswordDialogState by remember{
        mutableStateOf(false)
    }
    var updatedName by remember{
        mutableStateOf("")
    }

    var updatedEmail by remember{
        mutableStateOf("")
    }

    var profileUri by remember{
        
        mutableStateOf<Uri?>(null)
    }

    var username = sharedPrefrences.getString(Keys.USERNAME,"")

    var profilePicUrl = sharedPrefrences.getString(Keys.PROFILE_PIC_URL,"")

    var name = sharedPrefrences.getString(Keys.NAME,"")

    var updatedPhotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val jwtToken = sharedPrefrences.getString(Keys.JWT_TOKEN,"")

    var oldPassword by remember{
        mutableStateOf("")
    }

    var newPassword by remember{
        mutableStateOf("")
    }

    var confirmPassword by remember{
        mutableStateOf("")
    }

    var currrentPassword = sharedPrefrences.getString(Keys.PASSWORD,"")

    var medicalHistoryDialogstate by remember{
        mutableStateOf(false)
    }
    
    var medicalHistoryUpdateState by remember {
        mutableStateOf(false)
    }
    
    var height by remember{
        mutableStateOf("")
    }

    var weight by remember{
        mutableStateOf("")
    }

    var gender by remember{
        mutableStateOf("")
    }

    var chronicIllness by remember{
        mutableStateOf("")
    }

    var pastSurgeries by remember{
        mutableStateOf("")
    }

    var infections by remember{
        mutableStateOf("")
    }

    var allergies by remember{
        mutableStateOf("")
    }

    var allergicMedications by remember{
        mutableStateOf("")
    }

    var medicalHistory by remember{
        mutableStateOf<MedicalHistory?>(null)
    }

    LaunchedEffect(Unit) {

        var bearerToken = "Bearer $jwtToken"

        try{
            username?.let {
                var response = AuthApiInstance.api.getMedicalHistory(username = username.trim(),jwtToken=bearerToken)
                if(response.isSuccessful){
                    medicalHistory = response.body()
                    Log.d("MEDICAL_HISTORY_SUCCESS",medicalHistory.toString())
                }else
                    Log.d("MEDICAL_HISTORY_ERROR",response.errorBody().toString())
            }
        }catch (e:Exception){
            Log.d("MEDICAL_HISTORY_EXCEPTION",e.message.toString())
        }

    }




    if(medicalHistoryDialogstate){
        AlertDialog(onDismissRequest = {
            medicalHistoryDialogstate=false
        }
            , confirmButton = {
                Button(onClick = {
                    medicalHistoryUpdateState=!medicalHistoryUpdateState
                }
                , colors = ButtonDefaults
                        .buttonColors(
                            containerColor = Purple40
                        )) {
                    Text(text = "Update"
                    , fontSize = 17.sp
                    , fontWeight = FontWeight.W500)
                }
            }
        , dismissButton = {
            Button(onClick = {

            }
                , colors = ButtonDefaults
                    .buttonColors(
                        containerColor = Color.Transparent
                    )
            , modifier = Modifier
                    .border(width = 2.dp, color = Purple40, shape = CircleShape)) {
                Text(text = "Cancel"
                , fontSize = 17.sp
                , fontWeight = FontWeight.W500
                , color = Purple40)
            }
            }
        , title = {
            Text(text = "Medical History")
            }
        , text = {

            if(medicalHistory!=null){
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())) {

                    Text(text = "Height:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))

                    if(medicalHistoryUpdateState){
                        TextField(value = height
                            , onValueChange ={
                                height=it
                            }
                            , label = {
                                Text(text = "Height"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )
                    }else{
                        Text(text = "165 cm"
                            , fontSize = 16.sp
                            , color = Color.Black
                        )
                    }


                    Spacer(modifier = Modifier
                        .height(10.dp))



                    Text(text = "Weight:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))

                    if(medicalHistoryUpdateState){
                        TextField(value = weight
                            , onValueChange ={
                                weight=it
                            }
                            , label = {
                                Text(text = "Weight"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )
                    }else{
                        Text(text = "70 kg"
                            , fontSize = 16.sp
                            , color = Color.Black
                        )
                    }



                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = "Gender:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))

                    if(medicalHistoryUpdateState){
                        TextField(value = gender
                            , onValueChange ={
                                gender=it
                            }
                            , label = {
                                Text(text = "Gender"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )
                    }else{
                        Text(text = "M"
                            , fontSize = 16.sp
                            , color = Color.Black
                        )
                    }


                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = "Chronic Illness:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))

                    if(medicalHistoryUpdateState){
                        TextField(value = chronicIllness
                            , onValueChange ={
                                chronicIllness=it
                            }
                            , label = {
                                Text(text = "Chronic Illness"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )
                    }else{
                        Text(text = "Sulpha allergic"
                            , fontSize = 16.sp
                            , color = Color.Black
                        )
                    }



                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = "Past Surgeries:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))

                    if(medicalHistoryUpdateState){
                        TextField(value = pastSurgeries
                            , onValueChange ={
                                pastSurgeries=it
                            }
                            , label = {
                                Text(text = "Past Surgeries"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )
                    }else{
                        Text(text = "Appendectomy"
                            , fontSize = 16.sp
                            , color = Color.Black
                        )
                    }



                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = "Infections:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))

                    if(medicalHistoryUpdateState){
                        TextField(value = infections
                            , onValueChange ={
                                infections=it
                            }
                            , label = {
                                Text(text = "Infections"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )
                    }else{
                        Text(text = "Suplha"
                            , fontSize = 16.sp
                            , color = Color.Black
                        )
                    }



                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = "Allergies:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))

                    if(medicalHistoryUpdateState){
                        TextField(value = allergies
                            , onValueChange ={
                                allergies=it
                            }
                            , label = {
                                Text(text = "Allergies"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )
                    }else{
                        Text(text = "Pollen, Dust"
                            , fontSize = 16.sp
                            , color = Color.Black
                        )
                    }


                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = "Allergic Medications:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))

                    if(medicalHistoryUpdateState){
                        TextField(value = allergicMedications
                            , onValueChange ={
                                allergicMedications=it
                            }
                            , label = {
                                Text(text = "Allergic Medications"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )
                    }else{
                        Text(text = "Penicillin"
                            , fontSize = 16.sp
                            , color = Color.Black
                        )
                    }

                }
            }else{
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())) {

                    Text(text = "Height:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))


                        TextField(value = height
                            , onValueChange ={
                                height=it
                            }
                            , label = {
                                Text(text = "Height"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )


                    Spacer(modifier = Modifier
                        .height(10.dp))



                    Text(text = "Weight:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))

                    
                        TextField(value = weight
                            , onValueChange ={
                                weight=it
                            }
                            , label = {
                                Text(text = "Weight"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )



                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = "Gender:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))


                        TextField(value = gender
                            , onValueChange ={
                                gender=it
                            }
                            , label = {
                                Text(text = "Gender"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )



                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = "Chronic Illness:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))

                                            TextField(value = chronicIllness
                            , onValueChange ={
                                chronicIllness=it
                            }
                            , label = {
                                Text(text = "Chronic Illness"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )




                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = "Past Surgeries:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))


                        TextField(value = pastSurgeries
                            , onValueChange ={
                                pastSurgeries=it
                            }
                            , label = {
                                Text(text = "Past Surgeries"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )



                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = "Infections:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))


                        TextField(value = infections
                            , onValueChange ={
                                infections=it
                            }
                            , label = {
                                Text(text = "Infections"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )




                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = "Allergies:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))

                    TextField(value = allergies
                            , onValueChange ={
                                allergies=it
                            }
                            , label = {
                                Text(text = "Allergies"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )



                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = "Allergic Medications:"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(5.dp))


                        TextField(value = allergicMedications
                            , onValueChange ={
                                allergicMedications=it
                            }
                            , label = {
                                Text(text = "Allergic Medications"
                                    , fontSize = 16.sp
                                    , color = Color.Black)
                            }
                        )


                }
            }



            }
        , modifier = Modifier
                .height(500.dp))
    }

    if(updatePasswordDialogState){
        AlertDialog(onDismissRequest = {
            updatePasswordDialogState=false
        }, confirmButton = {
            Button(onClick = {

                var updatePassword = username?.let {
                    UpdatePassword(
                         password = oldPassword.trim(),newPassword=newPassword.trim()
                    )
                }

                coRoutineScope.launch {
                    try{

                        var response = updatePassword?.let { username?.let { it1 -> AuthApiInstance.api.updatePassword(it, jwtToken = "Bearer $jwtToken",username= it1.trim()) } }

                        if (response != null) {
                            if(response.isSuccessful){
                                var patient = response.body()
                                Log.d("UPDATE_PASSWORD_SUCCESS",response.body().toString())
                                editor.putString(Keys.PASSWORD,newPassword.trim())
                                updatePasswordDialogState=false
                            }else
                                Log.d("UPDATE_PASSWORD_ERROR",response.errorBody().toString())
                        }

                    }catch (e:Exception){
                        e.localizedMessage?.let { Log.d("UPDATE_PASSWORD_EXCEPTION", it) }
                    }
                }



            }
            , colors = ButtonDefaults.buttonColors(
                containerColor = if(!oldPassword.isEmpty()&&!newPassword.isEmpty()&&!confirmPassword.isEmpty()&&newPassword.equals(confirmPassword)&&oldPassword.equals(currrentPassword))
                    Purple40
                else
                    Color.Gray
            )) {
                Text(text = "Update"
                , fontWeight = FontWeight.W500,
                    fontSize = 18.sp)
            }
        }
        , dismissButton = {
            TextButton(onClick = {
                updatePasswordDialogState=false
            }
            , modifier = Modifier
                    .border(2.dp, Purple40, CircleShape)) {
                Text(text = "Cancel"
                , fontWeight = FontWeight.W500
                , fontSize = 18.sp)
            }
            }
            , title = {
                Text(text = "Update Password")
            }
        , text = {

            Column {

                Text(text = "Use a Strong, Unique Password – Create a password with at least 8-12 characters, including uppercase and lowercase letters, numbers, and special characters. Avoid common words or easily guessable information."
                , fontSize = 17.sp
                , fontWeight = FontWeight.W500)

                Spacer(modifier = Modifier
                    .height(5.dp))
                
                Text(text = "We strongly recommend logging in again after changing your password to continue enjoying the benefits of our services."
                , fontSize = 17.sp
                , fontWeight = FontWeight.W500)


                Spacer(modifier = Modifier
                    .height(15.dp))

                TextField(value = oldPassword, onValueChange ={
                    oldPassword=it
                }
                , label = {
                    Text(text = "Current Password"
                    , fontSize = 18.sp
                    , color = Color.Black)
                    })

                Spacer(modifier = Modifier
                    .height(15.dp))

                TextField(value = newPassword, onValueChange ={
                    newPassword=it
                }
                    , label = {
                        Text(text = "New Password"
                            , fontSize = 18.sp
                            , color = Color.Black)
                    })

                Spacer(modifier = Modifier
                    .height(15.dp))

                TextField(value =confirmPassword, onValueChange ={
                    confirmPassword=it
                }
                    , label = {
                        Text(text = "Confirm Password"
                            , fontSize = 18.sp
                            , color = Color.Black)
                    })

            }


            }
        )
    }


    if(editProfileDialogState){
        AlertDialog(onDismissRequest = {
            editProfileDialogState=false
        }, confirmButton = {
            Button(onClick = {

                var updatePatient = UpdatedPatient(
                    name = updatedName,
                    email = updatedEmail
                )

                try{

                    coRoutineScope.launch {
                        var response = username?.let { AuthApiInstance.api.updatePatientProfile(username= it, updatePatient = updatePatient, jwtToken = "Bearer $jwtToken") }

                        if (response != null) {
                            if(response.isSuccessful){
                                var patient = response.body()
                                Log.d("UPDATED_PROFILE_SUCCESSFULL", response.body().toString())
                                if (patient != null) {

                                    updatedPhotoUri?.let {
                                        if (username != null) {
                                            if (jwtToken != null) {

                                                Log.d("UPDATE_PROFILE_PIC_JWT_TOKEN",jwtToken.toString())

                                                uploadProfilePicture(context=context, imageUri = updatedPhotoUri!!, username=username, jwtToken = "$jwtToken"){ patient->

                                                    if(patient!=null){
                                                        editor.putString(Keys.NAME,patient.name)
                                                        editor.putString(Keys.EMAIL,patient.email)
                                                        editor.putString(Keys.PROFILE_PIC_URL,patient.profilePicUrl)
                                                        editor.commit()
                                                        editor.apply()
                                                        editProfileDialogState=false
                                                    }

                                                }

                                            }
                                        }
                                    }

                                }
                            }else
                                response.errorBody()
                                    ?.let { Log.d("UPDATED_PROFILE_ERROR", it.string()) }
                        }
                    }

                }catch (e:Exception){
                    Log.d("UPDATE_PROFILE_EXCEPTION",e.localizedMessage.toString())
                }
            }
            , colors = ButtonDefaults.buttonColors(
                containerColor = Purple40
            )) {
                Text(text = "Update"
                , fontSize = 18.sp
                , fontWeight = FontWeight.W500)
            }
        }
            , title = {
                Text(text = "Edit Profile")
            }
        , dismissButton = {
            Button(onClick = {
                editProfileDialogState=false
            }
            , colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
            , modifier = Modifier
                    .border(width = 2.dp, color = Purple40, shape = CircleShape)) {
                Text(text = "Cancle"
                , color = Purple40
                , fontSize = 18.sp
                , fontWeight = FontWeight.W500)
            }
            }
        , text = {
            Column {
                Box(modifier = Modifier
                    .fillMaxWidth()
                , contentAlignment = Alignment.Center){
                    GalleryPickerCompose { uri->
                        updatedPhotoUri=uri
                    }
                }

                Spacer(modifier = Modifier
                    .height(10.dp))

                TextField(value = updatedName
                    , onValueChange ={
                        updatedName=it
                    }
                , label = {
                    Text(text = "Name:"
                    , fontSize = 18.sp
                    , fontWeight = FontWeight.W500)
                    })

                Spacer(modifier = Modifier
                    .height(15.dp))

                TextField(value = updatedEmail, onValueChange = {
                    updatedEmail=it
                }
                , label = {
                    Text(text = "Email:"
                    , fontSize = 18.sp
                    , fontWeight = FontWeight.W500)
                    })

                Spacer(modifier = Modifier
                    .height(10.dp))

            }
            })
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(purpleGradient)
        .verticalScroll(scrollState)) {

        Text(text = "Profile"
        , fontWeight = FontWeight.W500
        , fontSize = 25.sp
        , color = Color.White
        , modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp)
        , textAlign = TextAlign.Center)

        Spacer(modifier = Modifier
            .height(100.dp))
        
        Box(modifier = Modifier
            .fillMaxSize()){

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(680.dp) // Increased height of the Canvas
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
                    brush = whiteBrush
                )
            }

            Column {

                Box(modifier = Modifier
                    .fillMaxWidth()
                , contentAlignment = Alignment.Center){
                    if(profilePicUrl==null){
                        Image(painter = painterResource(id = R.drawable.img)
                            , contentDescription = ""
                            , modifier = Modifier
                                .size(180.dp)
                                .offset(y = -50.dp)
                            , contentScale = ContentScale.Crop)
                    }else{
                        Image(painter = rememberAsyncImagePainter(model = profilePicUrl)
                            , contentDescription = ""
                            , modifier = Modifier
                                .size(180.dp)
                                .offset(y = -40.dp)
                                .clip(CircleShape)
                            , contentScale = ContentScale.Crop)
                    }

                }


                Text(text = name.toString()
                    , fontSize = 32.sp
                    , color = Color.Black
                , modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = -20.dp)
                , textAlign = TextAlign.Center
                , style = MaterialTheme.typography.titleMedium)

                Text(text = username.toString()
                , fontSize = 22.sp
                , style = MaterialTheme.typography.bodySmall
                , color = Color.Gray
                , modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = -10.dp)
                , textAlign = TextAlign.Center)

                Column(modifier = Modifier
                    .padding(10.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly
                        , modifier = Modifier
                            .padding(top = 20.dp)
                    ) {
                        Card(modifier = Modifier
                            .weight(1f)
                            .size(150.dp)
                            .padding(end = 5.dp)
                            .clickable {
                                medicalHistoryDialogstate = !medicalHistoryDialogstate
                            }
                            , colors = CardDefaults.cardColors(
                                contentColor = Color.White
                            )) {
                            Column(modifier = Modifier
                                .fillMaxSize()
                                .background(redGradientBrush)
                                , verticalArrangement = Arrangement.SpaceAround) {
                                Icon(imageVector = Icons.Default.DocumentScanner
                                    , contentDescription = ""
                                    , modifier = Modifier
                                        .padding(start = 10.dp, bottom = 15.dp)
                                        .size(40.dp))
                                Text(text = "Medical History"
                                    , modifier = Modifier
                                        .padding(start = 10.dp)
                                    , fontSize = 20.sp
                                    , fontWeight = FontWeight.W600
                                    , color = Color.White)
                            }
                        }
                        Card(modifier = Modifier
                            .weight(1f)
                            .size(150.dp)
                            .padding(start = 5.dp)
                            .clickable {
                                coRoutineScope.launch {
                                    pagerState.animateScrollToPage(1)
                                }
                            }
                            , colors = CardDefaults.cardColors(
                                contentColor = Color.White
                            )) {
                            Column(modifier = Modifier
                                .fillMaxSize()
                                .background(blueGradientBrush)
                                , verticalArrangement = Arrangement.SpaceAround) {
                                Icon(imageVector = Icons.Default.Search
                                    , contentDescription = ""
                                    , modifier = Modifier
                                        .padding(start = 10.dp, bottom = 15.dp)
                                        .size(40.dp))
                                Text(text = "Search"
                                    , modifier = Modifier
                                        .padding(start = 10.dp)
                                    , fontSize = 20.sp
                                    , fontWeight = FontWeight.W600
                                    , color = Color.White)
                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly
                        , modifier = Modifier
                            .padding(top = 20.dp)
                    ) {
                        Card(modifier = Modifier
                            .weight(1f)
                            .size(150.dp)
                            .padding(end = 5.dp)
                            .clickable {
                                editProfileDialogState = true
                            }
                            , colors = CardDefaults.cardColors(
                                contentColor = Color.White
                            )) {
                            Column(modifier = Modifier
                                .fillMaxSize()
                                .background(brightGreenGradient)
                                , verticalArrangement = Arrangement.SpaceAround) {
                                Icon(imageVector = Icons.Default.Edit
                                    , contentDescription = ""
                                    , modifier = Modifier
                                        .padding(start = 10.dp, bottom = 15.dp)
                                        .size(40.dp))
                                Text(text = "Edit Profile"
                                    , modifier = Modifier
                                        .padding(start = 10.dp)
                                    , fontSize = 20.sp
                                    , fontWeight = FontWeight.W600
                                    , color = Color.White)
                            }
                        }
                        Card(modifier = Modifier
                            .weight(1f)
                            .size(150.dp)
                            .padding(start = 5.dp)
                            .clickable {
                                updatePasswordDialogState = !updatePasswordDialogState
                            }
                            , colors = CardDefaults.cardColors(
                                contentColor = Color.White
                            )) {
                            Column(modifier = Modifier
                                .fillMaxSize()
                                .background(orangeGradient)
                                , verticalArrangement = Arrangement.SpaceAround) {
                                Icon(imageVector = Icons.Default.Key
                                    , contentDescription = ""
                                    , modifier = Modifier
                                        .padding(start = 10.dp, bottom = 15.dp)
                                        .size(40.dp))
                                Text(text = "Change Password"
                                    , modifier = Modifier
                                        .padding(start = 10.dp)
                                    , fontSize = 20.sp
                                    , fontWeight = FontWeight.W600
                                    , color = Color.White)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier
                    .height(10.dp))


                Box(modifier = Modifier
                    .fillMaxWidth()
                , contentAlignment = Alignment.Center){
                    Button(onClick = {
                        editor.putBoolean(Keys.LOGIN_STATUS,false)
                        editor.commit()
                        editor.apply()
                        navController.navigate(Routes.LOGIN_SCREEN_ROUTE.route){
                            popUpTo(0)
                        }
                    }
                        , shape = CircleShape
                        , colors = ButtonDefaults.buttonColors(
                            containerColor = BlueBackground
                        )
                        , modifier = Modifier
                            .width(200.dp)) {
                        Text(text = "Logout"
                            , fontSize = 20.sp
                            , fontWeight = FontWeight.W500
                            , style = MaterialTheme.typography.titleMedium)
                    }
                }

            }


        }

    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ProfileScreenPreview(){
    val pagerState = rememberPagerState(initialPage = 0) {
        Utility.getTabItemList().size
    }
    ProfileFragment(rememberNavController(), pagerState = pagerState)
}