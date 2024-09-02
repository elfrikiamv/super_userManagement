package com.elfrikiamv.super_usermanagement.viewmodel

// UserViewModel.kt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfrikiamv.super_usermanagement.model.Comment
import com.elfrikiamv.super_usermanagement.model.Post
import com.elfrikiamv.super_usermanagement.model.User
import com.elfrikiamv.super_usermanagement.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = UserRepository()

    // LiveData para usuarios
    private val _users = MutableLiveData<List<User>>(emptyList())
    val users: LiveData<List<User>> get() = _users

    private val _posts = MutableLiveData<List<Post>>(emptyList())
    val posts: LiveData<List<Post>> get() = _posts

    private val _comments = MutableLiveData<List<Comment>>(emptyList())
    val comments: LiveData<List<Comment>> get() = _comments

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
        val updatedUsers = _users.value?.map { user ->
            if (user.id == updatedUser.id) updatedUser else user
        } ?: emptyList()
        _users.postValue(updatedUsers) // Asegura la actualización
    }



    /*fun updateUser(updatedUser: User) {
        // Crea una nueva lista para asegurarte de que LiveData detecte el cambio
        val updatedUsers = _users.value?.map { user ->
            if (user.id == updatedUser.id) updatedUser else user
        } ?: emptyList()

        // Asigna la nueva lista a LiveData
        _users.value = updatedUsers

        // Luego puedes lanzar una corutina para sincronizar con el servidor (si decides implementar esto)
        *//*viewModelScope.launch {
            try {
                // Simula la actualización en el servidor
                repository.updateUserOnServer(updatedUser)
            } catch (e: Exception) {
                // Maneja el error si falla la sincronización con el servidor
            }
        }*//*
    }*/


}
