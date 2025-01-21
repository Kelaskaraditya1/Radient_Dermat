package com.starkindustries.radientdermat.Frontend.Screens.Patient.Fragments
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CaptureFragment(){
    Box(modifier = Modifier
        .fillMaxSize()
        , contentAlignment = Alignment.Center){
        Text(text = "Capture Fragment"
            , fontWeight = FontWeight.W500
            , fontSize = 18.sp)
    }
}