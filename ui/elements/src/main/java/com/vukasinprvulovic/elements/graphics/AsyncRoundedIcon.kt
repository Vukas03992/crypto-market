package com.vukasinprvulovic.elements.graphics

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class AsyncRoundedIconDesign(
    val width: Int,
    val height: Int,
    val padding: Int,
    val cornerRadius: Int,
    val background: Color,
) {
    val size = if (width > height) width else height
}

data class AsyncRoundedIconConfiguration(
    @DrawableRes val placeholder: Int,
    @DrawableRes val error: Int
)

@Composable
fun AsyncRoundedIcon(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String,
    design: AsyncRoundedIconDesign,
    configuration: AsyncRoundedIconConfiguration
) {
    Box(
        modifier = modifier
            .size(design.size.dp)
            .clip(RoundedCornerShape(design.cornerRadius.dp))
            .background(design.background),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = configuration.placeholder),
            error = painterResource(id = configuration.error),
            modifier = Modifier.fillMaxSize().padding(design.padding.dp)
        )
    }
}
