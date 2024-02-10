package com.rkcoding.newstime.newstime_feature.domain.repository

import com.rkcoding.newstime.newstime_feature.domain.model.Article
import com.rkcoding.newstime.utils.Resources

interface NewsRepository {

    suspend fun getTopHeadings(
        category : String
    ): Resources<List<Article>>

    suspend fun searchForNews(
        query: String
    ): Resources<List<Article>>

}