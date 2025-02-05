package com.starkindustries.radientdermat.Frontend.Screens.Compose

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.starkindustries.radientdermat.Frontend.Routes.Routes
import com.starkindustries.radientdermat.R
import com.starkindustries.radientdermat.ui.theme.BlueBackground
import com.starkindustries.radientdermat.ui.theme.cardBlueBackground

@Composable
fun AuthenticationLogoTextCompose(logo:Painter,text:String){
    Column() {
        Box(modifier = Modifier
            .fillMaxWidth()
            , contentAlignment = Alignment.Center) {
            Image(painter =logo
                , contentDescription = ""
                , modifier = Modifier
                    .size(130.dp)
                    .fillMaxWidth()
            )
        }

        Text(text = text
            , fontSize = 35.sp
            , fontWeight = FontWeight.Bold
            , modifier = Modifier.fillMaxWidth()
            , textAlign = TextAlign.Center)
    }
}


@Composable
fun SwitchScreenCompose(text1:String,text2:String,navController: NavController,route: String){
    Row(modifier = Modifier
        .fillMaxWidth()
        , horizontalArrangement = Arrangement.Center
        , verticalAlignment = Alignment.CenterVertically) {

        Text(text = "Don't have an account,"
            , fontSize = 18.sp
            , fontWeight = FontWeight.W500
            , color = Color.White)
        Text(text = " Signup"
            , fontSize = 18.sp
            , fontWeight = FontWeight.W700
            , color = BlueBackground
            , textDecoration = TextDecoration.Underline
            , modifier = Modifier
                .clickable {
                    navController.navigate(route)
                })
    }

}

@Composable
fun DiseaseTabCompose(){


    var scrollState = rememberScrollState()

    AlertDialog(onDismissRequest = {

    }, confirmButton = {

    }
    , text = {
        Column(modifier = Modifier
            .fillMaxWidth()
            .size(400.dp)
            .verticalScroll(scrollState)) {
            Image(painter = painterResource(id = R.drawable.login_logo)
                , contentDescription = ""
            , modifier = Modifier
                    .padding(10.dp))

            Spacer(modifier = Modifier
                .height(15.dp))

            Text(text = "Symtoms:"
            , style = MaterialTheme.typography.titleMedium
            , fontSize = 25.sp
            , textDecoration = TextDecoration.Underline)

            Spacer(modifier = Modifier
                .height(10.dp))

            Text(text = "disease symtoms are as follows"
            , fontWeight = FontWeight.W400
                , fontSize = 20.sp)

            Spacer(modifier = Modifier
                .height(20.dp))

            Text(text = "Symtoms:"
                , style = MaterialTheme.typography.titleMedium
                , fontSize = 25.sp
                , textDecoration = TextDecoration.Underline)

            Spacer(modifier = Modifier
                .height(10.dp))

            Text(text = "disease symtoms are as follows"
                , fontWeight = FontWeight.W400
                , fontSize = 20.sp)
        }
        }
    , title = {
        Text(text = "Disease Name"
        , modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        , textAlign = TextAlign.Center
        , fontWeight = FontWeight.W500
        , style = MaterialTheme.typography.titleLarge)
        })
}

@Composable
fun GalleryPickerCompose(getImageUri:(Uri)->Unit){
    var imageUri by remember{
        mutableStateOf<Uri?>(null)
    }

    var galleryLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri->
        imageUri=uri
        imageUri?.let { getImageUri(it) }
    }

    Box(modifier = Modifier
        .size(200.dp)
        .fillMaxSize()){
        Image(painter = if(imageUri==null)
                        painterResource(id = R.drawable.profile)
                        else
                            rememberAsyncImagePainter(model = imageUri)
            , contentDescription = ""
        , modifier = Modifier
                .size(190.dp)
                .clip(CircleShape)
        , contentScale = ContentScale.Crop)

        Box( modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 55.dp, end = 25.dp)
        , contentAlignment = Alignment.BottomEnd){
            Image(painter = painterResource(id = R.drawable.plus)
                , contentDescription = ""
            , modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                    .alpha(if (imageUri == null) 1f else 0f))
        }
    }


}

@Composable
fun CircularImageProfile(modifier: Modifier,uri: Uri?=null){

    Image(painter = rememberAsyncImagePainter(model = uri)
        , contentDescription = ""
    , modifier = modifier
            .size(180.dp)
            .offset(y=-30.dp)
            .clip(CircleShape)
    , contentScale = ContentScale.Crop)

}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Preview(){
CircularImageProfile(modifier = Modifier)
}