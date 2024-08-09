package com.elfrikiamv.super_usermanagement

// MainActivity.kt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elfrikiamv.super_usermanagement.ui.theme.Super_userManagementTheme
import com.elfrikiamv.super_usermanagement.view.UserDetailScreen
import com.elfrikiamv.super_usermanagement.view.UserListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Super_userManagementTheme {
                val navController = rememberNavController()

                // Setup NavHost with routes for UserListScreen and UserDetailScreen
                NavHost(navController = navController, startDestination = "userList") {
                    composable("userList") {
                        UserListScreen(navController)
                    }
                    composable("userDetail/{userId}") { backStackEntry ->
                        // Get userId from the arguments
                        val userId =
                            backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
                        UserDetailScreen(userId)
                    }
                }
            }
        }
    }
}
