package com.starkindustries.radientdermat.Utility

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.starkindustries.radientdermat.Data.TabItem
import com.starkindustries.radientdermat.Frontend.Screens.Patient.Data.DiseasesTab
import com.starkindustries.radientdermat.R

class Utility{
    companion object{
        fun getTabItemList():List<TabItem>{

            var list = listOf(
                TabItem(title = "Home", selectedIcon = Icons.Filled.Home, unSelectedIcon = Icons.Outlined.Home)
                , TabItem(title = "Capture", selectedIcon = Icons.Filled.Camera, unSelectedIcon = Icons.Outlined.Camera)
                , TabItem(title = "Profile", selectedIcon = Icons.Filled.Person, unSelectedIcon = Icons.Outlined.Person)
            )
            return list
        }

        fun getDiseasesTab():List<DiseasesTab>{
            return listOf(
                DiseasesTab(0,"Acne"),
                DiseasesTab(1,"Rashes"),
                DiseasesTab(2,"Hive"),
                DiseasesTab(3,"Eczema"),
                DiseasesTab(4,"Vitiligo"),
                DiseasesTab(5,"Measles"),
                DiseasesTab(6,"Shingles")
            )
        }

    }
}

