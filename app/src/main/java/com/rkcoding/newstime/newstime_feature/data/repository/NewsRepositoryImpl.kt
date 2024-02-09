package com.rkcoding.newstime.newstime_feature.data.repository

import com.rkcoding.newstime.newstime_feature.data.remote.NewsApi
import com.rkcoding.newstime.newstime_feature.domain.model.Article
import com.rkcoding.newstime.newstime_feature.domain.repository.NewsRepository
import com.rkcoding.newstime.utils.Resources
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
): NewsRepository {

    override suspend fun getTopHeadings(category: String): Resources<List<Article>> {
        return try {
            val news = api.getBreakingNews(category = category)
            Resources.Success(news.articles)
        }catch (e: Exception){
            Resources.Error(
                message = "Failed to fetch news ${e.message}"
            )
        }
    }

}