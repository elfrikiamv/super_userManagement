package com.elfrikiamv.super_usermanagement.viewmodel

// UserViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfrikiamv.super_usermanagement.model.User
import com.elfrikiamv.super_usermanagement.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel class that manages the UI-related data in a lifecycle-conscious way
class UserViewModel : ViewModel() {
    private val repository = UserRepository() // Instance of the repository to fetch data

    // MutableStateFlow to hold the list of users, initially empty
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users // Public immutable state flow for the users

    // Initialize by fetching the users when the ViewModel is created
    init {
        fetchUsers()
    }

    // Function to fetch the users and update the state flow
    private fun fetchUsers() {
        viewModelScope.launch {
            _users.value = repository.getUsers() // Update the state flow with the fetched users
        }
    }
}

