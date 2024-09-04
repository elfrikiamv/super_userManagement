package com.elfrikiamv.super_usermanagement.network

// ApiService.kt

import com.elfrikiamv.super_usermanagement.model.Comment
import com.elfrikiamv.super_usermanagement.model.Post
import com.elfrikiamv.super_usermanagement.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("comments")
    suspend fun getComments(): List<Comment>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") userId: Int)

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") userId: Int, @Body user: User)

}

