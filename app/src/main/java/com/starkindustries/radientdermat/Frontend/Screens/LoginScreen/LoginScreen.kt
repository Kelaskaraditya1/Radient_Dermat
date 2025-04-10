package com.starkindustries.radientdermat.Frontend.Screens.LoginScreen

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Display.Mode
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.starkindustries.radientdermat.R
import com.starkindustries.radientdermat.ui.theme.purpleGradient
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.starkindustries.radientdermat.Backend.Instance.AuthApiInstance
import com.starkindustries.radientdermat.Keys.Keys
import com.starkindustries.radientdermat.Frontend.Routes.Routes
import com.starkindustries.radientdermat.Frontend.Screens.Compose.AuthenticationLogoTextCompose
import com.starkindustries.radientdermat.Frontend.Screens.Compose.SwitchScreenCompose
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.LoginRequest
import com.starkindustries.radientdermat.ui.theme.BlueBackground
import com.starkindustries.radientdermat.ui.theme.Purple40
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController){

    var username by rememberSaveable{
        mutableStateOf("")
    }

    var password by remember{
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var forgotPasswordDialogState by remember{
        mutableStateOf(false)
    }

    var forgotPasswordEmail by remember{
        mutableStateOf("")
    }

    var emailSent by remember {
        mutableStateOf(false)
    }

    var otp by remember {
        mutableStateOf("")
    }

    var context = LocalContext.current.applicationContext

    var sharedPrefrences = context.getSharedPreferences(Keys.SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)
    var editor = sharedPrefrences.edit()


    val coroutineScope = rememberCoroutineScope()

    var forgotPasswordUpdatePasswordDialogState by remember {
        mutableStateOf(false)
    }

    var forgotPasswordPassword by remember {
        mutableStateOf("")
    }

    var isUsernameValid by remember{
        mutableStateOf(true)
    }

    var isPasswordValid by remember{
        mutableStateOf(true)
    }

    val passwordPattern = Regex("^(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+$).{9,}$")



    if(forgotPasswordUpdatePasswordDialogState){
        AlertDialog(onDismissRequest = {
            forgotPasswordUpdatePasswordDialogState=false
        }, confirmButton = {
            Button(onClick = {

                if(forgotPasswordPassword.isNotEmpty()){
                    coroutineScope.launch {
                        try {

                            var response = AuthApiInstance.api.forgotPassword(email = forgotPasswordEmail.trim(),forgotPasswordPassword.trim())
                            if(response.isSuccessful){
                                var patient = response.body()
                                Toast.makeText(context, "Password Updated Successfully!!", Toast.LENGTH_SHORT).show()
                                Log.d("FORGOT-PASSWORD-RESPONSE",response.body().toString())
                                forgotPasswordUpdatePasswordDialogState=false
                            }else{
                                Log.d("FORGOT-PASSWORD-FAILED",response.errorBody().toString())
                                Toast.makeText(context, "Failed to update password!!", Toast.LENGTH_SHORT).show()
                            }

                        }catch (e:Exception){
                            Log.d("FORGOT-PASSWORD-EXCEPTION",e.localizedMessage.toString())
                        }
                    }
                }

            }) {
                Text(text = "Submit"
                    , fontSize = 18.sp
                    , fontWeight = FontWeight.W500)
            }
        }
            , dismissButton = {
                Button(onClick = {
                    forgotPasswordUpdatePasswordDialogState=false
                }
                    , colors = ButtonDefaults
                        .buttonColors(
                            containerColor = Color.Transparent
                        )
                    , modifier = Modifier
                        .border(width = 2.dp, color = Purple40, shape = CircleShape)) {
                    Text(text = "Cancel"
                        , fontWeight = FontWeight.W500
                        , fontSize = 18.sp
                        , color = Purple40)
                }
            }
            , title = {
                Text(text = "Update Password")
            }
            , text = {
                Column {
                    Text(text = "Use a Strong, Unique Password – Create a password with at least 8-12 characters, including uppercase and lowercase letters, numbers, and special characters. Avoid common words or easily guessable information."
                        , fontSize = 17.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black)

                    Spacer(modifier = Modifier
                        .height(20.dp))

                    TextField(value = forgotPasswordPassword
                        , onValueChange ={
                            forgotPasswordPassword=it
                        }
                        , label = {
                            Text(text = "Password"
                                , fontWeight = FontWeight.W500
                                , fontSize = 17.sp
                                , color = Color.Black
                            )
                        })
                }
            })
    }

    if(forgotPasswordDialogState){
        AlertDialog(onDismissRequest = {
            forgotPasswordDialogState=!forgotPasswordDialogState
        }
            , confirmButton = {
                if(emailSent){
                    Button(onClick = {
                        if(otp.isNotEmpty()){
                            try{

                                coroutineScope.launch {
                                    var response = AuthApiInstance.api.verifyEmail(Integer.parseInt(otp))
                                    if(response.isSuccessful){
                                        Toast.makeText(context, "Account verified successfully!!", Toast.LENGTH_SHORT).show()
                                        Log.d("VERIFY_EMAIL_RESPONSE",response.body().toString())
                                        editor.putBoolean(Keys.ACCOUNT_VERIFICATION,true)
                                        editor.commit()
                                        editor.apply()
                                        otp=""
                                        forgotPasswordUpdatePasswordDialogState=true
                                        emailSent=false
                                        forgotPasswordDialogState=false
                                    }else{
                                        Log.d("VERIFY_EMAIL_FAILED",response.errorBody().toString())
                                        Toast.makeText(context, "Failed to verify account,Enter the correct Otp", Toast.LENGTH_SHORT).show()
                                    }

                                }

                            }catch (e:Exception){
                                Log.d("EMAIL_VERIFICATION_EXCEPTION", e.localizedMessage.toString())
                            }
                        }
                    }
                        , colors = ButtonDefaults
                            .buttonColors(
                                containerColor = Purple40
                            )) {
                        Text(text = "Verify-Otp"
                            , fontSize = 18.sp
                            , fontWeight = FontWeight.W500)
                    }
                }

            }
            , dismissButton = {
                Button(onClick = {
                    forgotPasswordEmail=""
                    otp=""
                    emailSent=false
                    forgotPasswordDialogState=false
                }
                    , colors = ButtonDefaults
                        .buttonColors(
                            containerColor = Color.Transparent
                        )
                    , modifier = Modifier
                        .border(width = 2.dp, color = Purple40, shape = CircleShape)) {
                    Text(text = "Cancel"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Purple40)
                }
            }
            , title = {
                Text(text = "Forgot Password")
            }
            , text = {
                Column {

                    Text(text = "Enter the valid email which has been used while registering , an otp will be sent to the email for account verification.Enter the correct otp for account verification."
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500)

                    Spacer(modifier = Modifier
                        .height(15.dp))

                    TextField(value = forgotPasswordEmail
                        , onValueChange ={
                            forgotPasswordEmail=it
                        }
                        , label = {
                            Text(text = "Email"
                                , fontSize = 18.sp
                                , fontWeight = FontWeight.W500
                                , color = Color.Black)
                        })

                    Spacer(modifier = Modifier
                        .height(15.dp))

                    Box(modifier =Modifier
                        .fillMaxWidth()
                        , contentAlignment = Alignment.Center){
                        Button(onClick = {
                            if (forgotPasswordEmail.isNotEmpty()) {
                                try {
                                    coroutineScope.launch {
                                        val response = AuthApiInstance.api.sendEmail(forgotPasswordEmail)
                                        if (response.isSuccessful) {
                                            val responseBody = response.body()?.string() // Read the response as a raw string
                                            Toast.makeText(context, "Email Sent Successfully!!", Toast.LENGTH_SHORT).show()
                                            Log.d("EMAIL_SENT_SUCCESSFULLY", responseBody ?: "Null response")

                                            Handler(Looper.getMainLooper()).post {
                                                emailSent = true
                                            }
                                        } else {
                                            Toast.makeText(context, "Failed to sent Email!! try again later.", Toast.LENGTH_SHORT).show()
                                            Log.d("EMAIL_SENT_FAILED", response.errorBody()?.string() ?: "Unknown error")
                                        }
                                    }
                                } catch (e: Exception) {
                                    Log.d("SENT_EMAIL_EXCEPTION", e.localizedMessage.toString())
                                }
                            }

                        }
                            , colors = ButtonDefaults
                                .buttonColors(
                                    containerColor = Purple40
                                )) {
                            Text(text = "Send Email"
                                , fontWeight = FontWeight.W500
                                , fontSize = 18.sp)
                        }
                    }

                    Spacer(modifier = Modifier
                        .height(50.dp))

                    if(emailSent){
                        TextField(value =otp ,
                            onValueChange ={
                                otp=it
                            }
                            , label = {
                                Text(text = "OTP"
                                    , fontSize = 18.sp
                                    , fontWeight = FontWeight.W500
                                    , color = Color.Black)
                            }
                        )

                    }

                }
            }
        )
    }


    Column(horizontalAlignment = Alignment.CenterHorizontally
        , verticalArrangement = Arrangement.Center
        , modifier = Modifier
            .fillMaxSize()) {


        Spacer(modifier = Modifier
            .height(40.dp))

        AuthenticationLogoTextCompose(logo = painterResource(id = R.drawable.login_logo), text = "Login"
            , modifier = Modifier)

        Spacer(modifier = Modifier
            .height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
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


            Column(horizontalAlignment = Alignment.Start
                , modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp)) {

                Spacer(modifier = Modifier
                    .height(130.dp))

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
                            Text(text = "Username should not be empty!!"
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
                        , focusedTextColor = Color.Black
                        , unfocusedIndicatorColor = Color.Transparent
                        , focusedIndicatorColor = Color.Transparent
                        ,unfocusedTextColor = Color.Black
                    ))

                Spacer(modifier = Modifier
                    .height(15.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it
                        isPasswordValid = passwordPattern.matches(it)},
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

                Button(onClick = {

                }
                    , modifier = Modifier
                        .padding(top = 10.dp)
                    , colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                        , contentColor = Color.White
                    )) {
                    Text(text = "forgot Password"
                        , fontSize = 16.sp
                        , modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                forgotPasswordDialogState = !forgotPasswordDialogState
                            }
                        , textAlign = TextAlign.End
                        , textDecoration = TextDecoration.Underline)
                }

                Spacer(modifier = Modifier
                    .height(20.dp))

                Box(modifier = Modifier
                    .fillMaxWidth()
                    , contentAlignment = Alignment.Center) {
                    Button(onClick = {

                        var loginRequest = LoginRequest(
                            username = username.toString().trim(),
                            password= password.toString().trim()
                        )

                        try{
                            coroutineScope.launch {
                                var response = AuthApiInstance.api.login(loginRequest)
                                if(response.isSuccessful){
                                    Log.d("JWT_TOKEN",response.body().toString())
                                    var jwtToken = response.body().toString()
                                    var anotherResponse = AuthApiInstance.api.getPatient(username=username.trim(), jwtToken = "Bearer ${jwtToken}")
                                    if(anotherResponse.isSuccessful){
                                        var patient = anotherResponse.body()
                                        Toast.makeText(context, "Welcome back "+username.trim()+" !!", Toast.LENGTH_SHORT).show()
                                        Handler(Looper.getMainLooper()).post{
                                            editor.putString(Keys.JWT_TOKEN,response.body().toString())
                                            editor.putBoolean(Keys.LOGIN_STATUS,true)
                                            if (patient != null) {
                                                editor.putString(Keys.NAME,patient.name)
                                            }
                                            if (patient != null) {
                                                editor.putString(Keys.USERNAME,patient.username)
                                            }
                                            editor.putString(Keys.USERNAME,username.trim())
                                            if (patient != null) {
                                                editor.putString(Keys.PROFILE_PIC_URL,patient.profilePicUrl)
                                            }
                                            editor.putString(Keys.PASSWORD,password.trim())
                                            editor.commit()
                                            editor.apply()
                                            navController.navigate(Routes.PATIENT_DASHBOARD_SCREEN_ROUTE.route){
                                                popUpTo(0){
                                                    inclusive=true

                                                }
                                            }
                                        }
                                    }

                                }else{
                                    Log.d("API_ERROR",response.errorBody().toString())
                                    Toast.makeText(context, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
                                }

                            }
                        }catch (e:Exception){
                            Log.d("LOGIN_EXCEPTION",e.localizedMessage.toString())
                        }





                    }
                        , shape = CircleShape
                        , colors = ButtonDefaults.buttonColors(
                            containerColor = BlueBackground
                        )) {
                        Text(text = "Login"
                            , fontSize = 18.sp
                            , fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier
                    .height(30.dp))


                Row(horizontalArrangement = Arrangement.SpaceEvenly
                    , verticalAlignment = Alignment.CenterVertically) {

                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 10.dp)
                            .background(Color.White)
                        , color = Color.White
                    )

                    Text(text = "Or"
                        , fontSize = 18.sp
                        , modifier = Modifier
                            .weight(0.2f)
                        , textAlign = TextAlign.Center
                        , color = Color.White
                        , fontWeight = FontWeight.W400)

                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp)
                            .background(Color.White)
                        , color = Color.White
                    )

                }

                Spacer(modifier = Modifier
                    .height(30.dp))

                Row(horizontalArrangement = Arrangement.Center
                    , modifier = Modifier
                        .fillMaxWidth()) {

                    Image(painter = painterResource(id = R.drawable.google)
                        , contentDescription = ""
                        , modifier = Modifier
                            .size(40.dp))

                    Spacer(modifier = Modifier
                        .width(20.dp))

                    Image(painter = painterResource(id = R.drawable.facebook)
                        , contentDescription = ""
                        , modifier = Modifier
                            .size(40.dp)
                        , colorFilter = ColorFilter.tint(Color.White))
                }

                Spacer(modifier = Modifier
                    .height(50.dp))

                SwitchScreenCompose(text1 = "Don't have an account,", text2 = "signup", navController = navController, route = Routes.SIGNUP_SCREEN_ROUTE.route)


            }
        }





    }



}




@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginScreenPreview(){
    LoginScreen(rememberNavController())
}