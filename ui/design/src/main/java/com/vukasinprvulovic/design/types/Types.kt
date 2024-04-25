package com.vukasinprvulovic.design.types

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vukasinprvulovic.design.colors.palettes.CryptoMarketplacePalette

val types = Typography(
    titleLarge = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.W500,
        fontSize = 22.sp,
        letterSpacing = (-0.64).sp,
        color = CryptoMarketplacePalette.black
    ),
    titleMedium = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
        letterSpacing = (0.16).sp,
        color = CryptoMarketplacePalette.blue
    ),
    labelLarge = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.W300,
        fontSize = 12.sp,
        letterSpacing = (0.2).sp,
        color = CryptoMarketplacePalette.blueDark
    ),
    labelSmall = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.W300,
        fontSize = 10.sp,
        letterSpacing = (0.8).sp,
        color = CryptoMarketplacePalette.blueDark
    ),
    bodyMedium = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        letterSpacing = (0.2).sp,
        color = CryptoMarketplacePalette.black
    ),
)
