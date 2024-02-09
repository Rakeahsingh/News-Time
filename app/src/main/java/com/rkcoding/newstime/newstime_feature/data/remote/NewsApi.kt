package com.rkcoding.newstime.newstime_feature.data.remote

import com.rkcoding.newstime.newstime_feature.domain.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("category") category: String,
        @Query("country") country: String = "us",
        @Query("apikey") apikey: String = API_KEY
    ): NewsResponse


    companion object{
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "2b3350c2e130493a94f280d8c05ca388"
    }

}