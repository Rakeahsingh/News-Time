package com.rkcoding.newstime.newstime_feature.domain.model

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)