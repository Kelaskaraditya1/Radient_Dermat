package com.starkindustries.radientdermat.Frontend.Screens.LoginScreen

import android.widget.Space
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.starkindustries.radientdermat.Frontend.Routes.Routes
import com.starkindustries.radientdermat.ui.theme.BlueBackground




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


    Column(horizontalAlignment = Alignment.CenterHorizontally
        , verticalArrangement = Arrangement.Center
    , modifier = Modifier
            .fillMaxSize()) {

        Box(modifier = Modifier
            .fillMaxWidth()
            , contentAlignment = Alignment.Center) {
            Image(painter = painterResource(id = R.drawable.login_logo)
                , contentDescription = ""
                , modifier = Modifier
                    .size(130.dp)
                    .fillMaxWidth()
            )
        }

        Text(text = "Login"
        , fontSize = 35.sp
        , fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier
            .height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
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
                    }
                , shape = CircleShape
                , label = {
                    Text(text = "Username"
                    , fontSize = 18.sp
                    , style = MaterialTheme.typography.titleMedium
                    , color = Color.Black
                    )
                }
                    , textStyle = TextStyle(fontSize = 18.sp))

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
                )

                Button(onClick = { }
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
                    , textAlign = TextAlign.End
                    , textDecoration = TextDecoration.Underline)
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                , contentAlignment = Alignment.Center) {
                    Button(onClick = {

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
                    .height(20.dp))


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
                    .height(20.dp))

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

                Row(modifier = Modifier
                    .fillMaxWidth()
                , horizontalArrangement = Arrangement.Center
                , verticalAlignment = Alignment.CenterVertically) {

                    Text(text = "Don't have an account,"
                    , fontSize = 18.sp
                    , fontWeight = FontWeight.W500
                    , color = Color.White)
                    Text(text = " Signup"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = BlueBackground
                    , textDecoration = TextDecoration.Underline
                    , modifier = Modifier
                            .clickable {
                                navController.navigate(Routes.SIGNUP_SCREEN_ROUTE.route)
                            })
                }


            }
        }





    }



 }




@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginScreenPreview(){
    LoginScreen(rememberNavController())
}