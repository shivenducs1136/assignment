package com.example.assignment.Navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.assignment.NewsView
import com.example.assignment.ui.AuthScreen
import com.example.assignment.ui.SearchScreen.SearchScreen

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun SetupNavGraph(navController: NavHostController,screen: String) {
    NavHost(
        navController = navController,
        startDestination = screen
    ) {
        composable(route = Screen.NewView.route){
            NewsView(navController = navController)
        }
        composable(route = Screen.SearchNews.route){
            SearchScreen(navController = navController)
        }
        composable(route=Screen.AuthScreen.route){
            AuthScreen(navController)
        }
    }
}