package com.rkcoding.newstime.newstime_feature.data.remote

import com.rkcoding.newstime.BuildConfig
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

    @GET("everything")
    suspend fun searchForNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse


    companion object{
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = BuildConfig.API_KEY
    }


}