package com.elfrikiamv.super_usermanagement.model

//Comment.kt

// Data class representing a Comment
data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)