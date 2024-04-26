package com.vukasinprvulovic.elements.features.marketplace.currency

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.vukasinprvulovic.elements.graphics.AsyncRoundedIcon
import com.vukasinprvulovic.elements.graphics.AsyncRoundedIconConfiguration
import com.vukasinprvulovic.elements.graphics.AsyncRoundedIconDesign

@Composable
fun MarketplaceCurrencyIcon(
    modifier: Modifier = Modifier,
    currencyName: String,
    currencyLogoUrl: String
) {
    AsyncRoundedIcon(
        modifier = modifier,
        imageUrl = currencyLogoUrl,
        contentDescription = currencyName,
        design = AsyncRoundedIconDesign(
            width = 64,
            height = 64,
            padding = 16,
            cornerRadius = 16,
            background = Color.White
        ),
        configuration = AsyncRoundedIconConfiguration(
            placeholder = com.vukasinprvulovic.design.R.drawable.logo,
            error = com.vukasinprvulovic.design.R.drawable.logo
        )
    )
}