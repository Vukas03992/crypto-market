package com.vukasinprvulovic.design.theming

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.vukasinprvulovic.design.colors.schemes.cryptoMarketplaceColorScheme
import com.vukasinprvulovic.design.types.types

@Composable
fun CryptoMarketTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = cryptoMarketplaceColorScheme,
        typography = types,
        content = content
    )
}