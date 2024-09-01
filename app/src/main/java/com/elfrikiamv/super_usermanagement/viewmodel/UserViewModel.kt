package com.elfrikiamv.super_usermanagement.viewmodel

// UserViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfrikiamv.super_usermanagement.model.Comment
import com.elfrikiamv.super_usermanagement.model.Post
import com.elfrikiamv.super_usermanagement.model.User
import com.elfrikiamv.super_usermanagement.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = UserRepository()

    // LiveData o StateFlow para usuarios
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> get() = _posts

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> get() = _comments

    init {
        fetchUsers()
        fetchPosts()
        fetchComments()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _users.value = repository.getUsers()
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _posts.value = repository.getPosts()
        }
    }

    private fun fetchComments() {
        viewModelScope.launch {
            _comments.value = repository.getComments()
        }
    }

    // Función para actualizar un usuario
    fun updateUser(updatedUser: User) {
        viewModelScope.launch {
            // Simula una actualización en la fuente de datos
            // Reemplaza esto con tu lógica real para actualizar el usuario
            val updatedUsers = _users.value.map { user ->
                if (user.id == updatedUser.id) updatedUser else user
            }
            _users.value = updatedUsers
        }
    }

    /*// Función para actualizar un usuario
    fun updateUser(updatedUser: User) {
        _users.value = _users.value.map { user ->
            if (user.id == updatedUser.id) updatedUser else user
        }
    }*/
}
