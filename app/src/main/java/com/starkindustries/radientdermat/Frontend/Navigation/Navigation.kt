package com.starkindustries.radientdermat.Frontend.Navigation

import SplashScreen
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.starkindustries.radientdermat.Frontend.Routes.Routes
import com.starkindustries.radientdermat.Frontend.Screens.LoginScreen.LoginScreen

@Composable
fun Navigation(){
    var navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SPLASH_SCREEN_ROUTE.route){

        composable(Routes.SPLASH_SCREEN_ROUTE.route){
            SplashScreen(navController)
        }
        
        composable(Routes.LOGIN_SCREEN_ROUTE.route){
            LoginScreen(navController = navController)
        }

    }

}