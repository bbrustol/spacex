package com.bbrustol.uikit.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun TextsCard(
    content: String,
    maxLines: Int = 1,
    style: SpanStyle = SpanStyle(
        fontWeight = FontWeight.W900,
        color = Color(0xFF4552B8),
        fontSize = 12.sp
    )
) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = style
            ) {
            append(content)
        }
        },
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}
