package com.rkcoding.newstime.newstime_feature.presentation.newsListScreen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rkcoding.newstime.R

@Composable
fun ImageHolder(
    imgUrl: String?,
    modifier: Modifier = Modifier
) {

    AsyncImage(
        model = imgUrl,
        contentDescription = "load Image",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .aspectRatio(16 / 9f),
        placeholder = painterResource(id = R.drawable.loading_news),
        error = painterResource(id = R.drawable.error_news)
    )

}