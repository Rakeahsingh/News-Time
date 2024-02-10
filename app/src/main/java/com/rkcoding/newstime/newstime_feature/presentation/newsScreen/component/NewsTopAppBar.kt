package com.rkcoding.newstime.newstime_feature.presentation.newsScreen.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(
    onSearchIconClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {

    TopAppBar(
        title = {
                Text(
                    text = "News Time",
                    fontWeight = FontWeight.Bold
                )
        },
        actions = {
            IconButton(onClick = onSearchIconClick
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search news"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        scrollBehavior = scrollBehavior
    )


}