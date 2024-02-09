package com.rkcoding.newstime.newstime_feature.presentation.newsListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.rkcoding.newstime.newstime_feature.presentation.newsListScreen.component.CategoryTabRow
import com.rkcoding.newstime.newstime_feature.presentation.newsListScreen.component.NewsArticleCard
import com.rkcoding.newstime.newstime_feature.presentation.newsListScreen.component.NewsTopAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class
)
@Composable
fun NewsScreen(
    viewModel: NewsViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pagerState = rememberPagerState()
    val category = listOf(
        "General", "Sports", "Business","Tech", "Education"
    )

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
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ){
                    items(viewModel.article){ article ->
                        NewsArticleCard(
                            article = article,
                            onArticleClick = {  }
                        )
                    }
                }
            }


        }

    }


}