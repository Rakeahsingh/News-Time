package com.rkcoding.newstime.newstime_feature.presentation.newsScreen

import com.rkcoding.newstime.newstime_feature.domain.model.Article

data class NewsScreenState(
    val isLoading: Boolean = false,
    val article: List<Article> = emptyList(),
    val error: String? = null,
    val isSearchbarVisible: Boolean = false,
    val selectedArticle: Article? = null,
    val category: String = "General",
    val searchQuery: String = ""
)
