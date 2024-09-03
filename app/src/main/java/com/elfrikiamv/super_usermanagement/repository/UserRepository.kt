package com.elfrikiamv.super_usermanagement.repository

// UserRepository.kt

import com.elfrikiamv.super_usermanagement.model.Comment
import com.elfrikiamv.super_usermanagement.model.Post
import com.elfrikiamv.super_usermanagement.model.User
import com.elfrikiamv.super_usermanagement.network.RetrofitInstance

class UserRepository {
    suspend fun getUsers(): List<User> {
        return RetrofitInstance.api.getUsers()
    }

    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }

    suspend fun getComments(): List<Comment> {
        return RetrofitInstance.api.getComments()
    }

    // Función para eliminar usuario en el servidor
    suspend fun deleteUser(userId: Int) {
        RetrofitInstance.api.deleteUser(userId)
    }
}

