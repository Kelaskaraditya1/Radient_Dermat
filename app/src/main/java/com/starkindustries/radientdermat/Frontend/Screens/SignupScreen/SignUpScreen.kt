package com.starkindustries.radientdermat.Frontend.Screens.SignupScreen

import android.content.Context
import android.widget.Space
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.starkindustries.radientdermat.Frontend.Keys.Keys
import com.starkindustries.radientdermat.Frontend.Routes.Routes
import com.starkindustries.radientdermat.Frontend.Screens.Compose.AuthenticationLogoTextCompose
import com.starkindustries.radientdermat.Frontend.Screens.Compose.SwitchScreenCompose
import com.starkindustries.radientdermat.R
import com.starkindustries.radientdermat.ui.theme.BlueBackground
import com.starkindustries.radientdermat.ui.theme.purpleGradient


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

    var context = LocalContext.current.applicationContext
    var sharedPrefrences = context.getSharedPreferences(Keys.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    var editor = sharedPrefrences.edit()

    Column(verticalArrangement = Arrangement.Center
    , modifier = Modifier
            .fillMaxSize()) {

        Spacer(modifier = Modifier
            .height(50.dp))

        AuthenticationLogoTextCompose(logo = painterResource(id = R.drawable.signup_logo), text = "Sign up")

        Spacer(modifier = Modifier
            .height(10.dp))

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
                    }
                    , shape = CircleShape
                    , label = {
                        Text(text = "Name"
                            , fontSize = 18.sp
                            , style = MaterialTheme.typography.titleMedium
                            , color = Color.Black
                        )
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
                    }
                    , shape = CircleShape
                    , label = {
                        Text(text = "Email"
                            , fontSize = 18.sp
                            , style = MaterialTheme.typography.titleMedium
                            , color = Color.Black
                        )
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
                    }
                    , shape = CircleShape
                    , label = {
                        Text(text = "Username"
                            , fontSize = 18.sp
                            , style = MaterialTheme.typography.titleMedium
                            , color = Color.Black
                        )
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
                    onValueChange = { password = it },
                    shape = CircleShape,
                    label = {
                        Text(
                            text = "Password",
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
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
                        navController.navigate(Routes.PATIENT_DASHBOARD_SCREEN_ROUTE.route)
                        editor.putBoolean(Keys.LOGIN_STATUS,true)
                        editor.commit()
                        editor.apply()
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

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SplashScreenPreview(){
    SignUpScreen(rememberNavController())
}