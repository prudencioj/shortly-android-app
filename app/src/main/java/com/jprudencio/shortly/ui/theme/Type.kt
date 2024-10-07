package com.jprudencio.shortly.ui.theme

import androidx.compose.material3.Typography
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
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

/*
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
)*/