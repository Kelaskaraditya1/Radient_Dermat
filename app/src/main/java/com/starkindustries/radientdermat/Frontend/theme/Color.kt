package com.starkindustries.radientdermat.Frontend.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val purpleGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF1A237E),
        Color(0xFF3949AB)
    )
)

val BlueBackground = Color(0xFF23A6F7)
val DarkBlue = Color(0xFF06154E)

val cardBlueBackground = Color(0xFF604DED)

val multiShadeYellowGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFFFF9C4), // Very Light Yellow
        Color(0xFFFFF176), // Light Yellow
        Color(0xFFFFEE58), // Medium Yellow
        Color(0xFFFFD600), // Bright Yellow
        Color(0xFFFFAB00)  // Deep Yellow
    )
)


val orangeGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFFF8A65), // Light Orange
        Color(0xFFFF5722)  // Bright Orange
    )
)

val brightGreenGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF00E676), // Bright Light Green
        Color(0xFF00C853)  // Vibrant Green
    )
)

val multiShadeOrangeGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFBF360C), // Dark Orange (Top)
        Color(0xFFFF7043), // Medium-Dark Orange
        Color(0xFFFF8A65), // Medium Orange
        Color(0xFFFFAB91), // Light Orange
        Color(0xFFD84315)  // Dark Orange (Bottom)
    )
)

val brightGreen = Color(0xFF43A047) // Vibrant Bright Green

val whiteBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFFFFFF), // Pure White
        Color(0xFFFFFFFF)  // Pure White (gradients with same color)
    )
)

val redGradientBrush = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFB71C1C), // Dark Red (Top)
        Color(0xFFD32F2F), // Medium Red
        Color(0xFFFF5252), // Light Red
        Color(0xFFFF1744)  // Bright Red (Bottom)
    )
)
val blueGradientBrush = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF0D47A1), // Dark Blue (Top)
        Color(0xFF1976D2), // Medium Blue
        Color(0xFF42A5F5)  // Light Blue (Bottom)
    )
)



