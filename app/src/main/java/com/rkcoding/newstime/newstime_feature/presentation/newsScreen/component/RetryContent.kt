package com.rkcoding.newstime.newstime_feature.presentation.newsScreen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun RetryContent(
    error: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = error,
            color = Color.Red,
            fontSize = 18.sp
        )

        Button(onClick = onRetry
        ) {
            Text(text = "Retry")
        }
    }

}