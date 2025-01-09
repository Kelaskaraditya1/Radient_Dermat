package com.starkindustries.radientdermat.Frontend.Screens.Patient

import android.content.Context
import android.widget.Space
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.starkindustries.radientdermat.Frontend.Keys.Keys
import com.starkindustries.radientdermat.Frontend.Routes.Routes

@Composable
fun PatientDashboardScreen(navController: NavController){

    var context = LocalContext.current.applicationContext
    var sharedPreferences = context.getSharedPreferences(Keys.SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    Box(modifier = Modifier
        .fillMaxSize()
        , contentAlignment = Alignment.Center){
        Column {
            Text(text = "DashBoard"
            , fontSize = 20.sp)
            Spacer(modifier = Modifier
                .height(10.dp))
            Button(onClick = {
                navController.navigate(Routes.LOGIN_SCREEN_ROUTE.route)
                editor.putBoolean(Keys.LOGIN_STATUS,false)
                editor.commit()
                editor.apply()
            }) {
                Text(text = "Logout"
                , fontWeight = FontWeight.W500
                , fontSize = 20.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PatientDashboardPreview(){
    PatientDashboardScreen(rememberNavController())
}