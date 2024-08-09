package com.elfrikiamv.super_usermanagement.repository

// UserRepository.kt

import com.elfrikiamv.super_usermanagement.model.User
import com.elfrikiamv.super_usermanagement.network.RetrofitInstance

// Repository class that interacts with the API service
class UserRepository {
    suspend fun getUsers(): List<User> {
        return RetrofitInstance.api.getUsers() // Fetches the list of users from the API
    }
}

