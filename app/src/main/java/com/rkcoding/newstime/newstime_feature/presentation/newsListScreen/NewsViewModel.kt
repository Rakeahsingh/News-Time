package com.rkcoding.newstime.newstime_feature.presentation.newsListScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkcoding.newstime.newstime_feature.domain.model.Article
import com.rkcoding.newstime.newstime_feature.domain.repository.NewsRepository
import com.rkcoding.newstime.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {

    var article by mutableStateOf<List<Article>>(emptyList())

    init {
        getNewsArticle(category = "General")
    }

    private fun getNewsArticle(category: String){
        viewModelScope.launch {
            val result = repository.getTopHeadings(category = category)
            when(result){
                is Resources.Error -> TODO()
                is Resources.Loading -> TODO()
                is Resources.Success -> {
                    article = result.data ?: emptyList()
                }
            }
        }
    }

}