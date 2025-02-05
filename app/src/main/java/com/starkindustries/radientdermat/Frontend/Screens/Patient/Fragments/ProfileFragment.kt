package com.starkindustries.radientdermat.Frontend.Screens.Patient.Fragments

import android.content.Context
import android.net.Uri
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.PhotoCameraBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.starkindustries.radientdermat.Frontend.Keys.Keys
import com.starkindustries.radientdermat.Frontend.Routes.Routes
import com.starkindustries.radientdermat.Frontend.Screens.Compose.CircularImageProfile
import com.starkindustries.radientdermat.Frontend.Screens.Compose.GalleryPickerCompose
import com.starkindustries.radientdermat.R
import com.starkindustries.radientdermat.ui.theme.BlueBackground
import com.starkindustries.radientdermat.ui.theme.Purple40
import com.starkindustries.radientdermat.ui.theme.blueGradientBrush
import com.starkindustries.radientdermat.ui.theme.brightGreenGradient
import com.starkindustries.radientdermat.ui.theme.cardBlueBackground
import com.starkindustries.radientdermat.ui.theme.multiShadeYellowGradient
import com.starkindustries.radientdermat.ui.theme.orangeGradient
import com.starkindustries.radientdermat.ui.theme.purpleGradient
import com.starkindustries.radientdermat.ui.theme.redGradientBrush
import com.starkindustries.radientdermat.ui.theme.whiteBrush

@Composable
fun ProfileFragment(navController:NavController){

    val scrollState = rememberScrollState()

    val context = LocalContext.current.applicationContext

    var coRoutineScope = rememberCoroutineScope()

    val sharedPrefrences = context.getSharedPreferences(Keys.SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)

    val editor = sharedPrefrences.edit()

    var editProfileDialogState by remember{
        mutableStateOf(false)
    }

    var updatePasswordDialogState by remember{
        mutableStateOf(false)
    }
    var updatedName by remember{
        mutableStateOf("")
    }

    var updatedEmail by remember{
        mutableStateOf("")
    }

    var profileUri by remember{
        
        mutableStateOf<Uri?>(null)
    }

    if(editProfileDialogState){
        AlertDialog(onDismissRequest = {
            editProfileDialogState=false
        }, confirmButton = {
            Button(onClick = {
                editProfileDialogState=false
                Toast.makeText(context, "Profile Updated Successfully!!", Toast.LENGTH_SHORT).show()
            }) {
                Text(text = "Update"
                , fontSize = 18.sp
                , fontWeight = FontWeight.W500)
            }
        }
        , dismissButton = {
            Button(onClick = {
                editProfileDialogState=false
            }
            , colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
            , modifier = Modifier
                    .border(width = 1.dp, color = Purple40, shape = CircleShape)) {
                Text(text = "Cancle"
                , color = Purple40)
            }
            }
        , text = {
            Column {
                Box(modifier = Modifier
                    .fillMaxWidth()
                , contentAlignment = Alignment.Center){
                    GalleryPickerCompose { uri->
                        profileUri=uri
                    }
                }

                Spacer(modifier = Modifier
                    .height(10.dp))

                TextField(value = updatedName
                    , onValueChange ={
                        updatedName=it
                    }
                , label = {
                    Text(text = "Name:"
                    , fontSize = 18.sp
                    , fontWeight = FontWeight.W500)
                    })

                Spacer(modifier = Modifier
                    .height(15.dp))

                TextField(value = updatedEmail, onValueChange = {
                    updatedEmail=it
                }
                , label = {
                    Text(text = "Email:"
                    , fontSize = 18.sp
                    , fontWeight = FontWeight.W500)
                    })

                Spacer(modifier = Modifier
                    .height(10.dp))

            }
            })
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(purpleGradient)
        .verticalScroll(scrollState)) {

        Text(text = "Profile"
        , fontWeight = FontWeight.W500
        , fontSize = 25.sp
        , color = Color.White
        , modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp)
        , textAlign = TextAlign.Center)

        Spacer(modifier = Modifier
            .height(100.dp))
        
        Box(modifier = Modifier
            .fillMaxSize()){

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(650.dp) // Increased height of the Canvas
            ) {
                val path = Path().apply {
                    // Start from the top-left corner
                    moveTo(0f, size.height * 0.145f)

                    // Create a large upward curve
                    quadraticBezierTo(
                        size.width * 0.4f,
                        size.height * 0.0006f,  // Higher control point for outward curve
                        size.width,
                        size.height * 0.1f
                    )

                    // Draw the rest of the shape down to the bottom
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(
                    path = path,
                    brush = whiteBrush
                )
            }

            Column {

                Box(modifier = Modifier
                    .fillMaxWidth()
                , contentAlignment = Alignment.Center){
                    if(profileUri==null){
                        Image(painter = painterResource(id = R.drawable.img)
                            , contentDescription = ""
                            , modifier = Modifier
                                .size(180.dp)
                                .offset(y = -50.dp)
                            , contentScale = ContentScale.Crop)
                    }else
                        CircularImageProfile(modifier = Modifier, uri = profileUri)

                }


                Text(text = "Aditya Kelaskar"
                    , fontSize = 30.sp
                    , color = Color.Black
                , modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = -20.dp)
                , textAlign = TextAlign.Center
                , style = MaterialTheme.typography.titleMedium)

                Text(text = "kelaskaraditya1"
                , fontSize = 20.sp
                , style = MaterialTheme.typography.bodySmall
                , color = Color.Gray
                , modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = -10.dp)
                , textAlign = TextAlign.Center)

                Column(modifier = Modifier
                    .padding(10.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly
                        , modifier = Modifier
                            .padding(top = 20.dp)
                    ) {
                        Card(modifier = Modifier
                            .weight(1f)
                            .size(150.dp)
                            .padding(end = 5.dp)
                            , colors = CardDefaults.cardColors(
                                contentColor = Color.White
                            )) {
                            Column(modifier = Modifier
                                .fillMaxSize()
                                .background(redGradientBrush)
                                , verticalArrangement = Arrangement.SpaceAround) {
                                Icon(imageVector = Icons.Default.DocumentScanner
                                    , contentDescription = ""
                                    , modifier = Modifier
                                        .padding(start = 10.dp, bottom = 15.dp)
                                        .size(40.dp))
                                Text(text = "Medical History"
                                    , modifier = Modifier
                                        .padding(start = 10.dp)
                                    , fontSize = 20.sp
                                    , fontWeight = FontWeight.W600
                                    , color = Color.White)
                            }
                        }
                        Card(modifier = Modifier
                            .weight(1f)
                            .size(150.dp)
                            .padding(start = 5.dp)
                            , colors = CardDefaults.cardColors(
                                contentColor = Color.White
                            )) {
                            Column(modifier = Modifier
                                .fillMaxSize()
                                .background(blueGradientBrush)
                                , verticalArrangement = Arrangement.SpaceAround) {
                                Icon(imageVector = Icons.Default.Search
                                    , contentDescription = ""
                                    , modifier = Modifier
                                        .padding(start = 10.dp, bottom = 15.dp)
                                        .size(40.dp))
                                Text(text = "Search"
                                    , modifier = Modifier
                                        .padding(start = 10.dp)
                                    , fontSize = 20.sp
                                    , fontWeight = FontWeight.W600
                                    , color = Color.White)
                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly
                        , modifier = Modifier
                            .padding(top = 20.dp)
                    ) {
                        Card(modifier = Modifier
                            .weight(1f)
                            .size(150.dp)
                            .padding(end = 5.dp)
                            .clickable {
                                editProfileDialogState = true
                            }
                            , colors = CardDefaults.cardColors(
                                contentColor = Color.White
                            )) {
                            Column(modifier = Modifier
                                .fillMaxSize()
                                .background(brightGreenGradient)
                                , verticalArrangement = Arrangement.SpaceAround) {
                                Icon(imageVector = Icons.Default.Edit
                                    , contentDescription = ""
                                    , modifier = Modifier
                                        .padding(start = 10.dp, bottom = 15.dp)
                                        .size(40.dp))
                                Text(text = "Edit Profile"
                                    , modifier = Modifier
                                        .padding(start = 10.dp)
                                    , fontSize = 20.sp
                                    , fontWeight = FontWeight.W600
                                    , color = Color.White)
                            }
                        }
                        Card(modifier = Modifier
                            .weight(1f)
                            .size(150.dp)
                            .padding(start = 5.dp)
                            , colors = CardDefaults.cardColors(
                                contentColor = Color.White
                            )) {
                            Column(modifier = Modifier
                                .fillMaxSize()
                                .background(orangeGradient)
                                , verticalArrangement = Arrangement.SpaceAround) {
                                Icon(imageVector = Icons.Default.Key
                                    , contentDescription = ""
                                    , modifier = Modifier
                                        .padding(start = 10.dp, bottom = 15.dp)
                                        .size(40.dp))
                                Text(text = "Change Password"
                                    , modifier = Modifier
                                        .padding(start = 10.dp)
                                    , fontSize = 20.sp
                                    , fontWeight = FontWeight.W600
                                    , color = Color.White)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier
                    .height(10.dp))


                Box(modifier = Modifier
                    .fillMaxWidth()
                , contentAlignment = Alignment.Center){
                    Button(onClick = {
                        editor.putBoolean(Keys.LOGIN_STATUS,false)
                        editor.commit()
                        editor.apply()
                        navController.navigate(Routes.LOGIN_SCREEN_ROUTE.route){
                            popUpTo(0)
                        }
                    }
                        , shape = CircleShape
                        , colors = ButtonDefaults.buttonColors(
                            containerColor = BlueBackground
                        )
                        , modifier = Modifier
                            .width(200.dp)) {
                        Text(text = "Logout"
                            , fontSize = 20.sp
                            , fontWeight = FontWeight.W500
                            , style = MaterialTheme.typography.titleMedium)
                    }
                }

            }


        }

    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ProfileScreenPreview(){
    ProfileFragment(rememberNavController())
}