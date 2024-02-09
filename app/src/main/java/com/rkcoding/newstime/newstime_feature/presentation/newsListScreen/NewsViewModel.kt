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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {

    var article by mutableStateOf<List<Article>>(emptyList())

    private val _state = MutableStateFlow(NewsScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: NewsScreenEvent){
        when(event){
            is NewsScreenEvent.OnArticleCardClick -> TODO()
            is NewsScreenEvent.OnCategoryChange -> {
                _state.update {
                    it.copy(
                        category = event.category
                    )
                }
                getNewsArticle(category = _state.value.category)
            }
            NewsScreenEvent.OnCloseIconClick -> TODO()
            NewsScreenEvent.OnSearchIconClick -> TODO()
            is NewsScreenEvent.OnSearchQueryChange -> TODO()
        }
    }

    init {
        getNewsArticle(category = "General")
    }

    private fun getNewsArticle(category: String){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = repository.getTopHeadings(category = category)
            when(result){
                is Resources.Error -> {
                    _state.update { it.copy(
                        article = emptyList(),
                        isLoading = false,
                        error = result.message
                    ) }
                }

                is Resources.Success -> {
                    _state.update { it.copy(
                        article = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    ) }

                }
            }
        }
    }

}