package com.elfrikiamv.super_usermanagement.navigation

// NavGraph.kt

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elfrikiamv.super_usermanagement.view.CommentsScreen
import com.elfrikiamv.super_usermanagement.view.UserDetailScreen
import com.elfrikiamv.super_usermanagement.view.UserListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.UserList.route) {
        composable(Screen.UserList.route) {
            UserListScreen(navController)
        }
        composable(Screen.UserDetail.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            UserDetailScreen(userId, navController)
        }
        composable(Screen.Comments.route) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")?.toIntOrNull() ?: 0
            CommentsScreen(postId)
        }
    }
}