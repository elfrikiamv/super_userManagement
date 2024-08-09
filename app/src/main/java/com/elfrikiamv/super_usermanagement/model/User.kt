package com.elfrikiamv.super_usermanagement.model

// User.kt

// Data class that represents a User with all relevant details
data class User(
    val id: Int,
    val name: String,          // User's name
    val username: String,      // User's username
    val email: String,         // User's email address
    val address: Address,      // User's address (another data class)
    val phone: String,         // User's phone number
    val website: String,       // User's website
    val company: Company       // User's company (another data class)
)

// Data class representing the User's address details
data class Address(
    val street: String,        // Street name
    val suite: String,         // Suite or apartment number
    val city: String,          // City name
    val zipcode: String,       // Zip code
    val geo: Geo               // Geographic location (another data class)
)

// Data class for geographic location (latitude and longitude)
data class Geo(
    val lat: String,           // Latitude
    val lng: String            // Longitude
)

// Data class representing the User's company details
data class Company(
    val name: String,          // Company name
    val catchPhrase: String,   // Company's catch phrase
    val bs: String             // Company's business or expertise area
)
