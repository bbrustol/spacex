package com.bbrustol.uikit.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun LoadImage(
    imageUrl:String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    if (LocalInspectionMode.current) {
        Box(modifier = modifier.background(Color.Magenta))
        return
    }
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        loading = {
            CircularProgressIndicator()
        },
        modifier = Modifier.clip(RectangleShape),
        contentScale = contentScale,
        contentDescription = contentDescription
    )
}