package com.starkindustries.radientdermat.Frontend.Screens.Patient.Fragments
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.starkindustries.radientdermat.Backend.Instance.TestApiInstance
import com.starkindustries.radientdermat.Keys.Keys
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.Patient
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.PatientTest
import com.starkindustries.radientdermat.R
import com.starkindustries.radientdermat.ui.theme.Purple40
import com.starkindustries.radientdermat.ui.theme.purpleGradient
import kotlinx.coroutines.launch

@Composable
fun DiseaseListItem(patientTest: PatientTest,bearerToken:String){

    var dialogState by remember {
        mutableStateOf(false)
    }

    var deleteDiseaseTabState by remember {
        mutableStateOf(false)
    }

    var coroutineScope = rememberCoroutineScope()

    if(deleteDiseaseTabState){

        AlertDialog(onDismissRequest = {
            deleteDiseaseTabState=false
        }, confirmButton = {
            Button(onClick = {
                try{
                    coroutineScope.launch {
                        var response =
                            patientTest.testId?.let { TestApiInstance.api.deleteTest(testId = it, jwtToken = bearerToken) }
                        if (response != null) {
                            if(response.isSuccessful){
                                if (response != null) {
                                    Log.d("DELETE_TEST_SUCCESS","NOTE DELETED SUCCESSFULLY,${response.body().toString()}")
                                    deleteDiseaseTabState=false
                                    dialogState=false
                                }else
                                    Log.d("DELETE_TEST_ERROR",response.errorBody().toString())
                            }
                        }
                    }
                }catch (e:Exception){
                    Log.d("DELETE_TEST_EXCEPTION",e.localizedMessage.toString())
                }

            }
                , colors = ButtonDefaults
                    .buttonColors(containerColor = Purple40)) {

                Text(text = "Delete"
                , fontSize = 17.sp
                , fontWeight = FontWeight.W500)

            }
        }
        , dismissButton = {

            TextButton(onClick = {
                deleteDiseaseTabState=false
            }
            , modifier = Modifier
                    .border(width = 2.dp, shape = CircleShape, color = Purple40)) {
                Text(text = "Cancel"
                , fontWeight = FontWeight.W500
                , fontSize = 17.sp)
            }

            }
            , text = {
                Text(text = "Are you sure you want to delete this test data? This action cannot be undone"
                , fontSize = 17.sp
                , fontWeight = FontWeight.W500)
            }
            , title = {
                Text(text = "Delete Test")
            }
        )
    }

    if(dialogState){

        AlertDialog(onDismissRequest = {
            dialogState=false
        }, confirmButton = {

            Button(onClick = {

                deleteDiseaseTabState=!deleteDiseaseTabState

            }
            , colors = ButtonDefaults
                    .buttonColors(containerColor = Purple40)) {
                Text(text = "Delete"
                , color = Color.White
                , fontWeight = FontWeight.W500
                , fontSize = 18.sp
                )
            }

        }
        , text = {
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())) {
                if(patientTest.diseaseImageUrl.isEmpty()||patientTest.diseaseImageUrl==null){
                    Image(painter = painterResource(id = R.drawable.placeholder)
                        , contentDescription = ""
                        , modifier = Modifier
                            .fillMaxWidth()
                            .size(width = 200.dp, height = 200.dp)
                        , contentScale = ContentScale.Crop)
                }else{
                    Image(
                        painter = rememberAsyncImagePainter(model = patientTest.diseaseImageUrl),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(width = 200.dp, height = 200.dp)
                        ,contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier
                    .height(25.dp))

                Column() {
                    Text(text = "Name:"
                        , textDecoration = TextDecoration.Underline
                        , fontSize = 20.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black)

                    Spacer(modifier = Modifier
                        .height(10.dp))

                    if(patientTest.diseaseName.isNotEmpty()){
                        Text(
                            text = buildAnnotatedString {
                                append("The disease displayed in the image is ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append(patientTest.diseaseName)
                                }
                            },
                            fontSize = 17.sp,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = "Symptoms:"
                        , textDecoration = TextDecoration.Underline
                        , fontSize = 20.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black)

                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = if(!patientTest.diseaseSymptoms.isEmpty()&&!patientTest.diseaseRemedy.isEmpty()&&!patientTest.diseaseName.isEmpty())
                        "${patientTest.diseaseSymptoms}"
                    else{
                        if(patientTest.diseaseName.isNotEmpty())
                            "Fetching data..."
                        else
                            ""
                    }
                        , fontSize = 17.sp
                        , color = Color.Black)

                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = "Remedy:"
                        , textDecoration = TextDecoration.Underline
                        , fontSize = 20.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black)

                    Spacer(modifier = Modifier
                        .height(10.dp))

                    Text(text = if(!patientTest.diseaseSymptoms.isEmpty()&&!patientTest.diseaseRemedy.isEmpty()&&!patientTest.diseaseName.isEmpty())
                        "${patientTest.diseaseRemedy}"
                    else {
                        if(patientTest.diseaseName.isNotEmpty())
                            "Fetching data..."
                        else
                            ""
                    }
                        , fontSize = 17.sp
                        , color = Color.Black)

                    Spacer(modifier = Modifier
                        .height(10.dp))




                }

            }
            }
        , title = {
            Text(text = patientTest.diseaseName)
            }
        , modifier = Modifier
                .height(500.dp))
    }



//    Box(modifier = Modifier
//        .padding(10.dp)
//        .size(250.dp)){
//        Card(modifier = Modifier
//            .size(250.dp)) {
//
//            Column(modifier = Modifier
//                .fillMaxSize()
//                , horizontalAlignment = Alignment.CenterHorizontally
//                , verticalArrangement = Arrangement.Center) {
//
//                if(patientTest.diseaseImageUrl.isNotEmpty()){
//
//                    Image(painter = rememberAsyncImagePainter(model = patientTest.diseaseImageUrl)
//                        , contentDescription = ""
//                        , modifier = Modifier
//                            .size(height = 200.dp, width = 200.dp)
//                            .padding(10.dp)
//                        , contentScale = ContentScale.Crop)
//
//                }else{
//                    Image(painter = painterResource(id = R.drawable.placeholder)
//                        , contentDescription =""
//                        , modifier = Modifier
//                            .size(width = 200.dp, height = 200.dp)
//                            .padding(bottom = 10.dp)
//                    )
//                }
//
//                Spacer(modifier = Modifier
//                    .height(5.dp))
//
//                Text(text = patientTest.diseaseName
//                    , fontSize = 20.sp
//                    , fontWeight = FontWeight.W500
//                    , color = Color.White
//                    , modifier = Modifier
//                        .padding(bottom = 5.dp)
//                )
//
//            }
//
//
//
//
//        }
//    }
    
Box(modifier = Modifier
    .size(250.dp)
    .padding(10.dp)
    .clip(RoundedCornerShape(15.dp))
    .clickable {
        dialogState = !dialogState
    }){

    if(patientTest.diseaseImageUrl.isNotEmpty()){
        Image(painter = rememberAsyncImagePainter(model = patientTest.diseaseImageUrl)
            , contentDescription = ""
            , modifier = Modifier
                .fillMaxSize()
            , contentScale = ContentScale.Crop)
    }else{
        Image(painter = painterResource(id = R.drawable.placeholder)
            , contentDescription = ""
            , modifier = Modifier
                .fillMaxSize()
            , contentScale = ContentScale.Crop)
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color.Black
                ), startY = 300f
            )
        ))

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 10.dp)
    , contentAlignment = Alignment.BottomCenter){
        if(patientTest.diseaseName.isNotEmpty()){
            Text(text = patientTest.diseaseName
                , fontWeight = FontWeight.W500
                , fontSize = 25.sp
                , color = Color.White
                , modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
                    .fillMaxWidth()
                , textAlign = TextAlign.Center
            )
        }else{
            Text(text ="Disease"
                , fontWeight = FontWeight.W500
                , fontSize = 25.sp
                , color = Color.White
            )
        }

    }
}


}

@Composable
fun CaptureFragment(){

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(Keys.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val username = sharedPreferences.getString(Keys.USERNAME,"")
    val jwtToken = sharedPreferences.getString(Keys.JWT_TOKEN,"")
    val bearerToken = "Bearer ${jwtToken}"
    var testList by remember{
        mutableStateOf<List<PatientTest>>(emptyList())
    }


    LaunchedEffect(Unit) {

        try{
            var response = username?.let { TestApiInstance.api.getTests(username = it, jwtToken = bearerToken) }
            if (response != null) {
                if(response.isSuccessful){
                    testList = response.body()?: emptyList()
                    Log.d("TEST_DATA",testList.toString())
                }else
                    Log.d("TEST_DATA_ERROR",response.errorBody().toString())
            }
        }catch (e:Exception){
            e.localizedMessage?.let { Log.d("TEST_DATA_EXCEPTION", it.toString()) }
        }

    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(purpleGradient)){

        if(testList.isNotEmpty()){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 columns
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(16.dp)
            ) {

                items(testList){ test->
                    DiseaseListItem(test, bearerToken = bearerToken)
                }
            }
        }else{

            Box(modifier = Modifier
                .fillMaxSize()
                , contentAlignment = Alignment.Center
            ){
                Text(text = "No Tests yet!!"
                , color = Color.White
                , fontSize = 25.sp
                , fontWeight = FontWeight.W500)
            }

        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CaptureFragmentPreview() {
    DiseaseListItem(patientTest =     PatientTest(
        testId = 1,
        diseaseName = "Keratosis pilaris",
        diseaseSymptoms = "Keratosis pilaris (KP) is a common, harmless skin condition that causes small, rough bumps, often described as 'chicken skin.' These bumps typically appear on the upper arms, thighs, buttocks, and cheeks, and sometimes the back...",
        diseaseRemedy = "Keratosis pilaris (KP) is a common, harmless skin condition that causes small, rough bumps, often on the upper arms, thighs, cheeks, or buttocks. While there's no cure for KP, regular moisturizing is key...",
        diseaseImageUrl = "https://res.cloudinary.com/dhdrzsxor/image/upload/v1742479003/jngc7t7kupndlk4h4w2g.jpg",
        username = "kelaskaraditya1"
    ),"")
}