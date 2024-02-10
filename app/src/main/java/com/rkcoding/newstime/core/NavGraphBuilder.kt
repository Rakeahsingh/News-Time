package com.rkcoding.newstime.core

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rkcoding.newstime.newstime_feature.presentation.articleScreen.ArticleScreen
import com.rkcoding.newstime.newstime_feature.presentation.newsScreen.NewsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraphBuilder() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.NewsScreen.route
    ){
      composable(
          route = Screen.NewsScreen.route
      ) {
          NewsScreen(navController = navController)
      }

        composable(
            route = Screen.ArticleScreen.route + "?web_url={web_url}",
            arguments = listOf(
                navArgument("web_url"){
                    type = NavType.StringType
                }
            )
        ){
            val url = it.arguments?.getString("web_url") ?: ""
            ArticleScreen(
                navController = navController,
                url = url
            )
        }

    }

}