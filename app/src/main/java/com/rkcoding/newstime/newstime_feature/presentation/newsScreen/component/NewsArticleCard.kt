package com.rkcoding.newstime.newstime_feature.presentation.newsScreen.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rkcoding.newstime.newstime_feature.domain.model.Article
import com.rkcoding.newstime.utils.dateFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onArticleClick: (Article) -> Unit
) {

    val date = dateFormatter(article.publishedAt)

    Card(
        modifier = modifier.clickable { onArticleClick(article) }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            ImageHolder(imgUrl = article.urlToImage)

            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = article.source.name ?: "",
                    style = MaterialTheme.typography.bodySmall,
                )

                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }

}