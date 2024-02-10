package com.rkcoding.newstime.core

sealed class Screen(val route: String) {

    data object NewsScreen: Screen("newsScreen")
    data object ArticleScreen: Screen("newsDetailsScreen")

}