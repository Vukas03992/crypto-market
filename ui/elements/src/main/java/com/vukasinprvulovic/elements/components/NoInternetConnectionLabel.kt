package com.vukasinprvulovic.elements.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NoInternetConnectionLabel(
    modifier: Modifier = Modifier,
    paddingBottom: Dp = 0.dp
) {
    Surface(
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    ) {
        Box(
            contentAlignment = androidx.compose.ui.Alignment.Center,
            modifier = modifier.background(MaterialTheme.colorScheme.primary).padding(bottom = paddingBottom)
        ) {
            Text(text = "No internet connection", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.padding(16.dp))
        }
    }
}

@Preview
@Composable
fun NoInternetConnectionLabelPreview() {
    NoInternetConnectionLabel()
}