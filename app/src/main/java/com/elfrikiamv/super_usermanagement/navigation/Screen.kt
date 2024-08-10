package com.elfrikiamv.super_usermanagement.navigation

// Screen.kt

// Sealed class to define the screens/routes in the app
sealed class Screen(val route: String) {
    data object UserList : Screen("userList")
    data object UserDetail : Screen("userDetail/{userId}") {
        // Function to create the route for the UserDetail screen with the specific userId
        fun createRoute(userId: Int) = "userDetail/$userId"
    }
}
