package com.rkcoding.newstime.newstime_feature.presentation.articleScreen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@Composable
fun ArticleScreen(
    url: String?,
    navController: NavController
) {

    val context = LocalContext.current
    var isLoading by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ArticleTopAppBar(
                onBackButtonClick = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ){
            AndroidView(
                factory = {
                    WebView(context).apply {
                        webViewClient = object : WebViewClient(){
                            override fun onPageFinished(view: WebView?, url: String?) {
                                isLoading = false
                            }
                        }
                        loadUrl(url ?: "")
                    }
                }
            )
            if (isLoading && url != null){
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primaryContainer
                )
            }

        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleTopAppBar(
    onBackButtonClick: () -> Unit
) {
    TopAppBar(
        title = {
                Text(
                    text = "Article",
                    style = MaterialTheme.typography.headlineMedium
                )
        },
        navigationIcon = {
            IconButton(onClick = onBackButtonClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}