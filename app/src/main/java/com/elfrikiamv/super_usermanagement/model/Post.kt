package com.elfrikiamv.super_usermanagement.model

//Post.kt

// Data class representing a Post
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
)