package com.bbrustol.features.home.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bbrustol.uikit.theme.SpacexTheme

@Composable
fun EmptyStateScreen() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(com.bbrustol.uikit.R.drawable.crash_rocket_with_bg),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .align(Alignment.CenterHorizontally).fillMaxWidth()
            )
        Column(modifier = Modifier
            .fillMaxWidth().padding(vertical = 80.dp)
            ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = "Results not found"
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
fun EmptyStateScreenPreview() {
    SpacexTheme {
        EmptyStateScreen()
    }
}
