import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.key.Key
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
import com.starkindustries.radientdermat.Frontend.Screens.LoginScreen.LoginScreen
import com.starkindustries.radientdermat.Frontend.Screens.SignupScreen.SignUpScreen
import com.starkindustries.radientdermat.R
import com.starkindustries.radientdermat.ui.theme.purpleGradient
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    var startAnimation by remember { mutableStateOf(false) }
    var showSlogan by remember { mutableStateOf(false) }

    var context = LocalContext.current.applicationContext
    var sharedPreferences = context.getSharedPreferences(Keys.SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isConnected = rememberUpdatedState(newValue = isInternetAvailable(connectivityManager))

    // Start animation with coroutine delay
    LaunchedEffect(isConnected.value) {
        delay(500)  // Delay before the text starts sliding
        startAnimation = true
        delay(1000)  // Wait for slide out animation to complete
        showSlogan = true
        delay(1000)

        if(isConnected.value){
            if(sharedPreferences.getBoolean(Keys.LOGIN_STATUS,false))
                navController.navigate(Routes.PATIENT_DASHBOARD_SCREEN_ROUTE.route){
                    popUpTo(0)
                }
            else
                navController.navigate(Routes.LOGIN_SCREEN_ROUTE.route){
                    popUpTo(0)
                }
        }else{
            navController.navigate(Routes.NO_INTERNET_SCREEN_ROUTE.route){
                popUpTo(0)
            }
        }

    }

    val radientOffset by animateFloatAsState(
        targetValue = if (startAnimation) -500f else 0f,  // Move to left
        animationSpec = tween(durationMillis = 1500, easing = LinearEasing), label = ""
    )
    val dermatOffset by animateFloatAsState(
        targetValue = if (startAnimation) 500f else 0f,  // Move to right
        animationSpec = tween(durationMillis = 1500, easing = LinearEasing), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(purpleGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Logo at the top
            Image(
                painter = painterResource(id = R.drawable.radient_dermat),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(250.dp)
                    .offset(y = 10.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )

            Spacer(modifier = Modifier.height(40.dp))

            if (!showSlogan) {
                // Radient Text (slides left)
                Text(
                    text = "Radient",
                    fontWeight = FontWeight.Bold,
                    fontSize = 50.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(x = radientOffset.dp)  // Apply animated offset
                        .padding(end = 110.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Dermat Text (slides right)
                Text(
                    text = "Dermat",
                    fontWeight = FontWeight.Bold,
                    fontSize = 50.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(x = dermatOffset.dp)  // Apply animated offset
                        .padding(start = 110.dp),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(180.dp))

            // Slogan Fade In
            AnimatedVisibility(
                visible = showSlogan,
                enter = fadeIn(animationSpec = tween(800))
            ) {
                Text(
                    text = "Designed for what matters",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.W500
                )
            }
        }
    }
}

fun isInternetAvailable(connectivityManager: ConnectivityManager): Boolean {
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SplashScreenPreview(){
    SplashScreen(rememberNavController())
}