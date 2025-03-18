package com.starkindustries.radientdermat.Frontend.Screens.Patient.Fragments

import android.content.Context
import android.net.Uri
import android.widget.Space
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.outlined.BrowseGallery
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.PhotoCameraBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.starkindustries.radientdermat.Frontend.Keys.Keys
import com.starkindustries.radientdermat.Frontend.Screens.Compose.DiseaseTabCompose
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.Message
import com.starkindustries.radientdermat.R
import com.starkindustries.radientdermat.Utility.Utility
import com.starkindustries.radientdermat.ui.theme.brightGreenGradient
import com.starkindustries.radientdermat.ui.theme.cardBlueBackground
import com.starkindustries.radientdermat.ui.theme.orangeGradient
import com.starkindustries.radientdermat.ui.theme.purpleGradient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun CustomAlertDialog(){

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    , verticalArrangement = Arrangement.Center) {
        Box(modifier = Modifier
            .height(450.dp)
            .width(300.dp)
            .background(Color.LightGray)){
            Column(modifier = Modifier
                .fillMaxSize()) {
                ChatScreen()
            }
        }
    }


}

@Composable
fun HomeFragment(){

    var selectedTab by remember{
        mutableStateOf(0)
    }

    var scrollState = rememberScrollState()

    var diseasesList = Utility.getDiseasesTab()

    var diseaseCardState by remember {
        mutableStateOf(false)
    }

    var imageUri by remember{
        mutableStateOf<Uri?>(null)
    }

    var dialogState by remember{
        mutableStateOf(true)
    }

    var isChatEnable by remember{
        mutableStateOf(false)
    }

    val galleryLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri->
        imageUri=uri;
    }

    var coroutinesScope = rememberCoroutineScope()

    var context = LocalContext.current.applicationContext

    var sharedPrefrences = context.getSharedPreferences(Keys.SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)

    var editor = sharedPrefrences.edit()

    var username = sharedPrefrences.getString(Keys.USERNAME,"")

    var profilePicUrl = sharedPrefrences.getString(Keys.PROFILE_PIC_URL,"")

    if(isChatEnable){
        AlertDialog(onDismissRequest = {
            isChatEnable=false
        },
            modifier = Modifier
                .height(550.dp),
            confirmButton = {
            },
            text = {
               ChatScreen()
            }
        )
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(purpleGradient)
        .padding(10.dp)
        .verticalScroll(scrollState)) {

        Spacer(modifier = Modifier
            .height(20.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
        , verticalAlignment = Alignment.CenterVertically
        , horizontalArrangement = Arrangement.SpaceBetween){
            Column(verticalArrangement = Arrangement.Center) {
                Text(text = "Greetings, $username"
                    , fontSize = 22.sp
                    , color = Color.White
                    , fontWeight = FontWeight.W600
                )

                Spacer(modifier = Modifier
                    .height(5.dp))

                Text(text = "We wish you have a great day!"
                    , fontSize = 17.sp
                    , color = Color.White
                , fontWeight = FontWeight.W500)
            }
            if(profilePicUrl!=null){
                Image(painter = rememberAsyncImagePainter(model = profilePicUrl)
                    , contentDescription =""
                    , modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                , contentScale = ContentScale.Crop)
            }else{
                Image(painter = painterResource(id = R.drawable.profile)
                    , contentDescription =""
                , modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                , contentScale = ContentScale.Crop)
            }

        }
        Spacer(modifier = Modifier
            .height(50.dp))

        LazyRow {
            items(diseasesList){ disease->
                Box(modifier = Modifier
                    .padding(5.dp)){
                    Card(modifier = Modifier
                        .height(80.dp)
                        .width(150.dp)
                        .clickable {
                            selectedTab = disease.index
                            diseaseCardState = true
                        }
                        , colors = CardDefaults.cardColors(
                            containerColor = if(selectedTab==disease.index) cardBlueBackground else Color.Gray
                        )) {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            , contentAlignment = Alignment.Center){
                            Text(text = disease.name
                                , modifier = Modifier
                                , textAlign = TextAlign.Center
                                , fontWeight = FontWeight.W500
                                , fontSize = 23.sp
                                , color = Color.White)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier
            .height(40.dp))

        Text(text = "Take a Test"
        , fontSize = 40.sp
        , fontWeight = FontWeight.W500
        , color = Color.White)

        Spacer(modifier = Modifier
            .height(10.dp))

        Text(text = "Upload an image for taking an instant test."
        , fontSize = 22.sp
        , fontWeight = FontWeight.W400
        , color = Color.White)

        Spacer(modifier = Modifier
            .height(5.dp))

        Text(text = "You can upload photo from gallery or open camera for real time test."
        , fontSize = 22.sp
        , fontWeight = FontWeight.W400
        , color = Color.White)

        Spacer(modifier = Modifier
            .height(15.dp))


        if(imageUri!=null&&dialogState){
            AlertDialog(onDismissRequest = {
                dialogState=true
                imageUri=null
            }
                , confirmButton = {
                    Button(onClick = {
                        Toast.makeText(context, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show()
                        dialogState=false
                    }) {
                        Text(text = "Upload")
                    }
            }
            , text = {
                Column {
                    Image(painter = rememberAsyncImagePainter(model = imageUri)
                        , contentDescription =""
                    , modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .padding(5.dp)
                    , contentScale = ContentScale.Crop)
                }
                }
            , title = {
                Text(text = "Test"
                , fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                , textAlign = TextAlign.Center)
                }
            , dismissButton = {
                Button(onClick = {
                    dialogState=false
                }
                , colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF6650a4)
                )
                , modifier = Modifier
                        .border(width = 1.dp, color = Color(0xFF6650a4), shape = CircleShape)) {
                    Text(text = "Cancle")
                }
                })
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
            , colors = CardDefaults.cardColors(
                contentColor = Color.White
            )) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(brightGreenGradient)
                , verticalArrangement = Arrangement.SpaceAround) {
                    Icon(imageVector = Icons.Outlined.CameraAlt
                        , contentDescription = ""
                    , modifier = Modifier
                            .padding(start = 10.dp, bottom = 15.dp)
                            .size(40.dp))
                    Text(text = "Click a Photo"
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
                .clickable {
                    galleryLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                    if (imageUri != null)
                        dialogState = true

                }
                , colors = CardDefaults.cardColors(
                    contentColor = Color.White
                )) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(orangeGradient)
                    , verticalArrangement = Arrangement.SpaceAround) {
                    Icon(imageVector = Icons.Outlined.PhotoCameraBack
                        , contentDescription = ""
                        , modifier = Modifier
                            .padding(start = 10.dp, bottom = 15.dp)
                            .size(40.dp))
                    Text(text = "Upload from Gallery"
                        , modifier = Modifier
                            .padding(start = 10.dp)
                        , fontSize = 20.sp
                        , fontWeight = FontWeight.W600
                        , color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier
            .height(15.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(end = 5.dp)
        , contentAlignment = Alignment.BottomEnd){
            FloatingActionButton(onClick = {
                isChatEnable=!isChatEnable
            }
            , containerColor =  Color(0xFF00E676)) {
                Icon(imageVector = Icons.Default.ChatBubble
                    , contentDescription = ""
                , tint = Color.White)
            }
        }
    }

    if(diseaseCardState){

        var disease = Utility.getDiseasesTab().get(selectedTab)

        AlertDialog(onDismissRequest = {
            diseaseCardState=false
        }, confirmButton = {

        }
            , text = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .size(400.dp)
                    .verticalScroll(scrollState)) {
                    Image(painter = disease.painter
                        , contentDescription = ""
                        , modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .height(200.dp))

                    Spacer(modifier = Modifier
                        .height(15.dp))

                    Text(text = "Symtoms:"
                        , style = MaterialTheme.typography.titleMedium
                        , fontSize = 25.sp
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = disease.symtom
                        , fontWeight = FontWeight.W400
                        , fontSize = 20.sp)

                    Spacer(modifier = Modifier
                        .height(20.dp))

                    Text(text = "Remedy:"
                        , style = MaterialTheme.typography.titleMedium
                        , fontSize = 25.sp
                        , textDecoration = TextDecoration.Underline)

                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = disease.remedy
                        , fontWeight = FontWeight.W400
                        , fontSize = 20.sp)
                }
            }
            , title = {
                Text(text = disease.name
                    , modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                    , textAlign = TextAlign.Center
                    , fontWeight = FontWeight.W500
                    , style = MaterialTheme.typography.titleLarge)
            })    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeFragment()

}