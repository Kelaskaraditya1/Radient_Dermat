package com.starkindustries.radientdermat.Frontend.Screens.Patient.Data

import androidx.compose.ui.graphics.painter.Painter

data class DiseasesTab(
    var index:Int,
    var name:String,
    var isSelected:Boolean?=false,
    var painter:Painter,
    var symtom:String,
    var remedy:String
)
