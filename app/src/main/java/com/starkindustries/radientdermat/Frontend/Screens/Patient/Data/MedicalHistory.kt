package com.starkindustries.radientdermat.Frontend.Screens.Patient.Data

data class MedicalHistory(
    val medicalHistoryId:Int?=null,
    val height:String,
    val weight:String,
    val gender:String,
    val chronicIllness:String,
    val pastSurgeries:String,
    val infections:String,
    val allergies:String,
    val allergicMedications:String,
    val username:String
)
