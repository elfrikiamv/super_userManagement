package com.elfrikiamv.super_usermanagement.navigation

// Screen.kt

sealed class Screen(val route: String) {
    object UserList : Screen("userList")
    object UserDetail : Screen("userDetail/{userId}") {
        // Método helper para crear la ruta con el userId
        fun createRoute(userId: Int) = "userDetail/$userId"
    }

    object Comments : Screen("comments/{postId}") {
        // Método helper para crear la ruta con el postId
        fun createRoute(postId: Int) = "comments/$postId"
    }
}

