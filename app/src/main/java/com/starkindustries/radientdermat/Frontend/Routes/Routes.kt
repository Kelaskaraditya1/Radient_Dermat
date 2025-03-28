package com.starkindustries.radientdermat.Frontend.Routes

import com.starkindustries.radientdermat.Keys.Keys

sealed class Routes(var route:String) {

    object SPLASH_SCREEN_ROUTE : Routes(Keys.SPLASH_SCREEN_ROUTE)
    object LOGIN_SCREEN_ROUTE : Routes(Keys.LOGIN_SCREEN_ROUTE)
    object SIGNUP_SCREEN_ROUTE : Routes(Keys.SIGNUP_SCREEN_ROUTE)
    object PATIENT_DASHBOARD_SCREEN_ROUTE : Routes(Keys.PATIENT_DASHBOARD_SCREEN_ROUTE)
    object HOME_FRAGMENT_ROUTE : Routes(Keys.HOME_FRAGMENT)
    object CAPTURE_FRAGMENT_ROUTE : Routes(Keys.CAPTURE_FRAGMENT)
    object PROFILE_FRAGMENT_ROUTE : Routes(Keys.PROFILE_FRAGMENT)
    object NO_INTERNET_SCREEN_ROUTE : Routes(Keys.NO_INTERNET_SCREEN)

}