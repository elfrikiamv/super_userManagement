package com.elfrikiamv.super_usermanagement.repository

// UserRepository.kt
import com.elfrikiamv.super_usermanagement.model.User
import com.elfrikiamv.super_usermanagement.network.RetrofitInstance

class UserRepository {
    suspend fun getUsers(): List<User> {
        return RetrofitInstance.api.getUsers()
    }
}
