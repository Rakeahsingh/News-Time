package com.rkcoding.newstime.newstime_feature.presentation.newsListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.rkcoding.newstime.newstime_feature.domain.model.Article
import com.rkcoding.newstime.newstime_feature.presentation.newsListScreen.component.BottomSheetContent
import com.rkcoding.newstime.newstime_feature.presentation.newsListScreen.component.CategoryTabRow
import com.rkcoding.newstime.newstime_feature.presentation.newsListScreen.component.NewsArticleCard
import com.rkcoding.newstime.newstime_feature.presentation.newsListScreen.component.NewsTopAppBar
import com.rkcoding.newstime.newstime_feature.presentation.newsListScreen.component.RetryContent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class
)
@Composable
fun NewsScreen(
    viewModel: NewsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pagerState = rememberPagerState()
    val category = listOf(
        "General", "Business", "Health", "Science", "Sports", "Technology", "Entertainment"
    )

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var shouldBottomSheetShow by remember {
        mutableStateOf(false)
    }

    if (shouldBottomSheetShow){
        ModalBottomSheet(
            onDismissRequest = { shouldBottomSheetShow = false },
            sheetState = sheetState,
            content = {
                state.selectedArticle?.let { article ->
                    BottomSheetContent(
                        article = article,
                        onButtonClick = {  }
                    )
                }
            }
        )
    }

    LaunchedEffect(key1 = pagerState){
        snapshotFlow { pagerState.currentPage }.collectLatest { page ->
            viewModel.onEvent(NewsScreenEvent.OnCategoryChange(category = category[page]))
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            NewsTopAppBar(
                scrollBehavior = scrollBehaviour,
                onSearchIconClick = { }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            CategoryTabRow(
                pagerState = pagerState,
                category = category,
                onTabSelected = { index ->
                    scope.launch { pagerState.animateScrollToPage(index) }
                }
            )

            HorizontalPager(
                count = category.size,
                state = pagerState
            ) {

                NewsArticleList(
                    state = state,
                    onCardClick = {
                        shouldBottomSheetShow = true
                    },
                    onRetry = {
                        viewModel.onEvent(NewsScreenEvent.OnCategoryChange(category = state.category))
                    }
                )

            }


        }

    }


}


@Composable
fun NewsArticleList(
    state: NewsScreenState,
    onCardClick: (Article) -> Unit,
    onRetry: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ){
        items(state.article){ article ->
            NewsArticleCard(
                article = article,
                onArticleClick = onCardClick
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if (state.isLoading){
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }
        if (state.error != null){
            RetryContent(
                error = state.error,
                onRetry = onRetry
            )
        }
    }
}