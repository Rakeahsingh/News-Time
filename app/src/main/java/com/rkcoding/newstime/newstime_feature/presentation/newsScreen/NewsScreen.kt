package com.rkcoding.newstime.newstime_feature.presentation.newsScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.rkcoding.newstime.core.Screen
import com.rkcoding.newstime.newstime_feature.domain.model.Article
import com.rkcoding.newstime.newstime_feature.presentation.newsScreen.component.BottomSheetContent
import com.rkcoding.newstime.newstime_feature.presentation.newsScreen.component.CategoryTabRow
import com.rkcoding.newstime.newstime_feature.presentation.newsScreen.component.NewsArticleCard
import com.rkcoding.newstime.newstime_feature.presentation.newsScreen.component.NewsTopAppBar
import com.rkcoding.newstime.newstime_feature.presentation.newsScreen.component.RetryContent
import com.rkcoding.newstime.newstime_feature.presentation.newsScreen.component.SearchAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class, ExperimentalComposeUiApi::class
)
@Composable
fun NewsScreen(
    navController: NavController,
    viewModel: NewsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pagerState = rememberPagerState()
    val category = listOf(
        "General", "Business", "Health", "Science", "Sports", "Technology", "Entertainment"
    )

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var shouldBottomSheetShow by remember {
        mutableStateOf(false)
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    if (shouldBottomSheetShow) {
        ModalBottomSheet(
            onDismissRequest = { shouldBottomSheetShow = false },
            sheetState = sheetState,
            content = {
                state.selectedArticle?.let {
                    BottomSheetContent(
                        article = it,
                        onReadFullStoryButtonClicked = {
                            navController.navigate(
                                Screen.ArticleScreen.route + "?web_url=${it.url}"
                            )

                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) shouldBottomSheetShow = false
                            }
                        }
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

    LaunchedEffect(key1 = Unit){
        if (state.searchQuery.isNotEmpty()){
            viewModel.onEvent(NewsScreenEvent.OnSearchQueryChange(searchQuery = state.searchQuery))
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Crossfade(targetState = state.isSearchbarVisible, label = "search bar") { isVisible ->
            if (isVisible){
                Column {
                    SearchAppBar(
                        modifier = Modifier.focusRequester(focusRequester),
                        value = state.searchQuery,
                        onValueChange = {
                            viewModel.onEvent(NewsScreenEvent.OnSearchQueryChange(it))
                        },
                        onSearchIconClick = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        },
                        onCloseIconClick = {
                            viewModel.onEvent(NewsScreenEvent.OnCloseIconClick)
                        }
                    )

                    NewsArticleList(
                        state = state,
                        onCardClick = { article ->
                            shouldBottomSheetShow = true
                            viewModel.onEvent(NewsScreenEvent.OnArticleCardClick(article = article))
                            Log.d("TAG", "NewsScreen: $article")
                        },
                        onRetry = {
                            viewModel.onEvent(NewsScreenEvent.OnCategoryChange(category = state.category))
                        }
                    )
                }

            }else{
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
                    topBar = {
                        NewsTopAppBar(
                            scrollBehavior = scrollBehaviour,
                            onSearchIconClick = {
                                scope.launch {
                                    delay(500)
                                    focusRequester.requestFocus()
                                }
                                viewModel.onEvent(NewsScreenEvent.OnSearchIconClick)
                            }
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
                                onCardClick = { article ->
                                    shouldBottomSheetShow = true
                                    viewModel.onEvent(NewsScreenEvent.OnArticleCardClick(article = article))
                                    Log.d("TAG", "NewsScreen: $article")
                                },
                                onRetry = {
                                    viewModel.onEvent(NewsScreenEvent.OnCategoryChange(category = state.category))
                                }
                            )

                        }

                    }

                }
            }
        }
    }


}


@RequiresApi(Build.VERSION_CODES.O)
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