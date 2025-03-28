package com.starkindustries.radientdermat.Frontend.Screens.Patient.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import com.google.ai.client.generativeai.GenerativeModel
import com.starkindustries.radientdermat.Keys.Keys
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.Message
import com.starkindustries.radientdermat.R
import com.starkindustries.radientdermat.ui.theme.Purple40
import com.starkindustries.radientdermat.ui.theme.purpleGradient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    var messageText by remember { mutableStateOf(TextFieldValue("")) }
    var messages by remember { mutableStateOf(listOf<Message>()) }

    var messageRequest by remember {
        mutableStateOf("")
    }

    var coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()) {
        // Chat Messages
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                ChatBubble(message)
            }
        }

        // Input Field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .weight(2f),
                shape = CircleShape,
                colors = TextFieldDefaults
                    .textFieldColors(
                        containerColor = Color(0xFFEDE7F6),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                        ,disabledIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        coroutineScope.launch {
                            if (messageText.text.isNotBlank()) {
                                messages = messages + Message(messageText.text, isUser = true)
                                messages = messages + Message(getBotResponse(messageText.text), isUser = false)
                                messageText = TextFieldValue("")
                            }
                        }
                    }
                ),
                placeholder = { Text("Type a message...") }
            )

            Box(modifier = Modifier){
                FloatingActionButton(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(45.dp)
                    ,onClick = {

                        coroutineScope.launch {
                            if (messageText.text.isNotBlank()) {
                                messageRequest = messageText.text
                                messageText = TextFieldValue("")
                                messages = messages + Message(messageRequest, isUser = true)
                                messages = messages + Message(getBotResponse(messageRequest), isUser = false)

                            }
                        }


                    }
                    , shape = CircleShape
                    , containerColor = Purple40)
                {
                    Icon(painter = painterResource(id = R.drawable.send)
                        , contentDescription = ""
                        , tint = Color.White
                    , modifier = Modifier
                            .padding(5.dp))
                }
            }

        }
    }
}

@Composable
fun ChatBubble(message: Message) {

    var context = LocalContext.current

    var sharedPrefrences = context.getSharedPreferences(Keys.SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)

    var editor = sharedPrefrences.edit()

    val profileImageUrl = sharedPrefrences.getString(Keys.PROFILE_PIC_URL,"")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Row {



            Box(
                modifier = Modifier
                    .background(
                        if (message.isUser) Color.Green else Color.Gray,
                        RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = message.text,
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier
                .width(5.dp))
            
            if(message.isUser){
                Image(painter = rememberAsyncImagePainter(model = profileImageUrl),
                    contentDescription = ""
                    , modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                    , contentScale = ContentScale.Crop)
            }
        }

    }
}

// Simulated bot response
suspend fun getBotResponse(userInput: String): String {

    var generativeModel = GenerativeModel(
        modelName = Keys.MODEL_NAME,
        apiKey = Keys.API_KEY
    )

    var prompt = userInput

    var response = generativeModel.generateContent(prompt).text

    Log.d("API_RESPONSE",response.toString())

    return response.toString()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewChatScreen() {
    ChatScreen()
}