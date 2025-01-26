package com.starkindustries.radientdermat.Frontend.Screens.Patient

import android.content.Context
import android.widget.ImageButton
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.starkindustries.radientdermat.Frontend.Keys.Keys
import com.starkindustries.radientdermat.Frontend.Routes.Routes
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Fragments.CaptureFragment
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Fragments.HomeFragment
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Fragments.ProfileFragment
import com.starkindustries.radientdermat.Utility.Utility
import com.starkindustries.radientdermat.ui.theme.purpleGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatiendDashboardScreen(navController: NavController) {

    val context = LocalContext.current.applicationContext
    val sharedPreferences = context.getSharedPreferences(Keys.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val pagerState = rememberPagerState(initialPage = 0) {
        Utility.getTabItemList().size
    }
    val activity = LocalContext.current as? ComponentActivity


    var selectedTab by remember { mutableIntStateOf(0) }

    LaunchedEffect(selectedTab) {
        pagerState.animateScrollToPage(selectedTab)
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedTab = pagerState.currentPage
    }

    val fragmentNavController = rememberNavController()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Radient Dermat") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.Black
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        activity?.finish()
                    }
                    , colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    )) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
                , actions = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = ""
                        , modifier = Modifier
                                .padding(end = 10.dp)
                                .size(30.dp)
                        , tint = Color.White)
                    }
                }
                , modifier = Modifier
                    .background(Color(0xFF1A237E))
            )
        }
    ) { paddingValues ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

            // TabRow
            TabRow(selectedTabIndex = selectedTab
            , modifier = Modifier
                    .fillMaxWidth()
            , containerColor = Color(0xFF1A237E)) {
                Utility.getTabItemList().forEachIndexed { index, item ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                        },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == index) item.selectedIcon else item.unSelectedIcon,
                                contentDescription = null
                            , tint = if(selectedTab==index)
                                Color.White
                            else
                            Color.LightGray)
                        },
                        text = {
                            Text(text = item.title
                            , color = if(selectedTab==index)
                            Color.White
                            else
                            Color.LightGray)
                        }
                    )
                }
            }

            // ViewPager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index ->
                when (index) {
                    0 -> HomeFragment()
                    1 -> CaptureFragment()
                    2 -> ProfileFragment()
                }
            }

        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PatientDashboardPreview(){
    PatiendDashboardScreen(navController = rememberNavController())
}