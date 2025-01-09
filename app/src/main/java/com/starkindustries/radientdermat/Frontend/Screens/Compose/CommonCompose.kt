package com.starkindustries.radientdermat.Frontend.Screens.Compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.starkindustries.radientdermat.Frontend.Routes.Routes
import com.starkindustries.radientdermat.R
import com.starkindustries.radientdermat.ui.theme.BlueBackground

@Composable
fun AuthenticationLogoTextCompose(logo:Painter,text:String){
    Column() {
        Box(modifier = Modifier
            .fillMaxWidth()
            , contentAlignment = Alignment.Center) {
            Image(painter =logo
                , contentDescription = ""
                , modifier = Modifier
                    .size(130.dp)
                    .fillMaxWidth()
            )
        }

        Text(text = text
            , fontSize = 35.sp
            , fontWeight = FontWeight.Bold
            , modifier = Modifier.fillMaxWidth()
            , textAlign = TextAlign.Center)
    }
}


@Composable
fun SwitchScreenCompose(text1:String,text2:String,navController: NavController,route: String){
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
            , fontWeight = FontWeight.W700
            , color = BlueBackground
            , textDecoration = TextDecoration.Underline
            , modifier = Modifier
                .clickable {
                    navController.navigate(route)
                })
    }

}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Preview(){

    SwitchScreenCompose(text1 = "already have an account,", text2 = "Login", navController = rememberNavController() , route = Routes.LOGIN_SCREEN_ROUTE.route)

}