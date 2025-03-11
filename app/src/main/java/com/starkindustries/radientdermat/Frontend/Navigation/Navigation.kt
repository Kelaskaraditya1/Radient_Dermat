package com.starkindustries.radientdermat.Frontend.Navigation

import SplashScreen
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.starkindustries.radientdermat.Frontend.Routes.Routes
import com.starkindustries.radientdermat.Frontend.Screens.Compose.NoInternetScreen
import com.starkindustries.radientdermat.Frontend.Screens.LoginScreen.LoginScreen
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Fragments.CaptureFragment
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Fragments.HomeFragment
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Fragments.ProfileFragment
import com.starkindustries.radientdermat.Frontend.Screens.Patient.PatiendDashboardScreen
import com.starkindustries.radientdermat.Frontend.Screens.SignupScreen.SignUpScreen

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

        composable(Routes.SIGNUP_SCREEN_ROUTE.route){
            SignUpScreen(navController)
        }

        composable(Routes.PATIENT_DASHBOARD_SCREEN_ROUTE.route){
            PatiendDashboardScreen(navController)
        }
        composable(Routes.HOME_FRAGMENT_ROUTE.route){
            HomeFragment()
        }
        composable(Routes.CAPTURE_FRAGMENT_ROUTE.route){
            CaptureFragment()
        }
        composable(Routes.PROFILE_FRAGMENT_ROUTE.route){
            ProfileFragment(navController)

        }
        composable(Routes.NO_INTERNET_SCREEN_ROUTE.route){
            NoInternetScreen(navController)
        }
    }

}