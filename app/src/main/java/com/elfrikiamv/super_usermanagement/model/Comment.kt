package com.elfrikiamv.super_usermanagement.model

//Comment.kt

// Data class representing a Comment
data class Comment(
    val id: Int,
    val body: String,
    val postId: Int,
    val name: String
)