package com.starkindustries.radientdermat.Frontend.Routes

import com.starkindustries.radientdermat.Frontend.Keys.Keys

sealed class Routes(var route:String) {

    object SPLASH_SCREEN_ROUTE : Routes(Keys.SPLASH_SCREEN_ROUTE)
    object LOGIN_SCREEN_ROUTE : Routes(Keys.LOGIN_SCREEN_ROUTE)
    object SIGNUP_SCREEN_ROUTE : Routes(Keys.SIGNUP_SCREEN_ROUTE)
    object PATIENT_DASHBOARD_SCREEN_ROUTE : Routes(Keys.PATIENT_DASHBOARD_SCREEN_ROUTE)
}