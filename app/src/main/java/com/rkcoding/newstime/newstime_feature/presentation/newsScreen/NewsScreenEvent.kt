package com.rkcoding.newstime.newstime_feature.presentation.newsScreen

import com.rkcoding.newstime.newstime_feature.domain.model.Article

sealed class NewsScreenEvent {

    data class OnArticleCardClick(val article: Article): NewsScreenEvent()

    data class OnCategoryChange(val category: String): NewsScreenEvent()

    data class OnSearchQueryChange(val searchQuery: String): NewsScreenEvent()

    data object OnSearchIconClick: NewsScreenEvent()

    data object OnCloseIconClick: NewsScreenEvent()

}