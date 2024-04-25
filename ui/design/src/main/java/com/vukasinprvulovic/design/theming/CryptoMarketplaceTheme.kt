package com.vukasinprvulovic.design.theming

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.vukasinprvulovic.design.colors.schemes.cryptoMarketplaceColorScheme

@Composable
fun CryptoMarketTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = cryptoMarketplaceColorScheme,
        content = content
    )
}