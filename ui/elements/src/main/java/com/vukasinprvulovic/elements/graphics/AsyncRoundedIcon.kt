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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers

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

    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .dispatcher(Dispatchers.IO)
        .memoryCacheKey(imageUrl)
        .diskCacheKey(imageUrl)
        .placeholder(configuration.placeholder)
        .error(configuration.error)
        .fallback(configuration.placeholder)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

    Box(
        modifier = modifier
            .size(design.size.dp)
            .clip(RoundedCornerShape(design.cornerRadius.dp))
            .background(design.background),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().padding(design.padding.dp)
        )
    }
}
