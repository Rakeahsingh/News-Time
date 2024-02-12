package com.rkcoding.newstime.newstime_feature.presentation.newsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkcoding.newstime.newstime_feature.domain.repository.NewsRepository
import com.rkcoding.newstime.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {


    private val _state = MutableStateFlow(NewsScreenState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    fun onEvent(event: NewsScreenEvent){
        when(event){

            is NewsScreenEvent.OnArticleCardClick -> {
                _state.update {
                    it.copy(
                        selectedArticle = event.article
                    )
                }
            }

            is NewsScreenEvent.OnCategoryChange -> {
                _state.update {
                    it.copy(
                        category = event.category
                    )
                }
                getNewsArticle(category = _state.value.category)
            }

            NewsScreenEvent.OnCloseIconClick -> {
                _state.update {
                    it.copy(
                        isSearchbarVisible = false
                    )
                }
                getNewsArticle(category = _state.value.category)
            }

            NewsScreenEvent.OnSearchIconClick -> {
                _state.update {
                    it.copy(
                        isSearchbarVisible = true,
                        article = emptyList()
                    )
                }
            }

            is NewsScreenEvent.OnSearchQueryChange -> {
                _state.update { it.copy(
                    searchQuery = event.searchQuery
                ) }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(1000)
                    searchForNews(query = _state.value.searchQuery)
                }

            }

        }
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

    private fun searchForNews(query: String){
        if (query.isEmpty()){
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = repository.searchForNews(query = query)
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