package com.starkindustries.radientdermat.Frontend.Activity

import SplashScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.starkindustries.radientdermat.Frontend.Navigation.Navigation
import com.starkindustries.radientdermat.Frontend.Screens.LoginScreen.LoginScreen
import com.starkindustries.radientdermat.ui.theme.RadientDermatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RadientDermatTheme {
                Application()
            }
        }
    }
}


@Composable
fun Application(){
    var navController = rememberNavController()
    Navigation()
}

