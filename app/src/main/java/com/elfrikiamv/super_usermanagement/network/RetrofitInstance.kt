package com.elfrikiamv.super_usermanagement.network

// RetrofitInstance.kt

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Object that holds the Retrofit instance for making API calls
object RetrofitInstance {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/" // Base URL for the API

    // Lazy initialization of the ApiService
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Set the base URL for Retrofit
            .addConverterFactory(GsonConverterFactory.create()) // Converter for JSON to Kotlin objects
            .build() // Build the Retrofit instance
            .create(ApiService::class.java) // Create the ApiService implementation
    }
}

