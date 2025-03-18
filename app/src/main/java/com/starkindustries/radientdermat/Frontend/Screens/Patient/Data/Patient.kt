package com.starkindustries.radientdermat.Frontend.Screens.Patient.Data

data class SignupRequest(
    val name:String,
    val email:String,
    val username:String,
    val password:String,
    val profilePicUrl:String
)

data class LoginRequest(
    val username:String,
    val password:String
)

data class Patient(
    val email: String,
    val name: String,
    val password: String,
    val profilePicUrl: String,
    val username: String
)

data class PatientsResponse(
    val status: String,
    val message: String
)

data class LoginResponse(
    val jwtToken:String
)

data class UpdatedPatient(
    val name:String,
    val email:String,
    val profilePicUri:String
)

