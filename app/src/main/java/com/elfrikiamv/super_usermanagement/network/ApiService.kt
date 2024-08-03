package com.elfrikiamv.super_usermanagement.network

// ApiService.kt
import com.elfrikiamv.super_usermanagement.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}
