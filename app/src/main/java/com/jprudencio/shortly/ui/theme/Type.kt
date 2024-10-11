package com.jprudencio.shortly.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jprudencio.shortly.R

private val Poppins = FontFamily(
    Font(R.font.poppins_bold),
    Font(R.font.poppins_medium),
    Font(R.font.poppins_regular)
)

val Typography = Typography(
    defaultFontFamily = Poppins,
    h1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp
    )
)