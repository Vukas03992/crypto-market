package com.vukasinprvulovic.design.colors.schemes

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.vukasinprvulovic.design.colors.palettes.CryptoMarketplacePalette

val cryptoMarketplaceColorScheme = lightColorScheme(
    primary = CryptoMarketplacePalette.blue,
    onPrimary = Color.White,
    primaryContainer = CryptoMarketplacePalette.gray,
    onPrimaryContainer = CryptoMarketplacePalette.black,
    secondary = CryptoMarketplacePalette.blueLight,
    onSecondary = CryptoMarketplacePalette.blueDark,
    secondaryContainer = CryptoMarketplacePalette.gray,
    onSecondaryContainer = CryptoMarketplacePalette.black,
    background = Color.White,
    onBackground = CryptoMarketplacePalette.black,
    surface = CryptoMarketplacePalette.gray,
    onSurface = CryptoMarketplacePalette.black,
)