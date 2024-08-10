package com.elfrikiamv.super_usermanagement.view

// UserDetailScreen.kt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.elfrikiamv.super_usermanagement.model.User
import com.elfrikiamv.super_usermanagement.viewmodel.UserViewModel

// Composable function to display the user's details
@Composable
fun UserDetailScreen(userId: Int, viewModel: UserViewModel = viewModel()) {
    // Get the selected user by filtering the list of users
    val user = viewModel.users.collectAsState().value.find { it.id == userId }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("User Details") }) // Top app bar with title
        },
        content = { paddingValues -> // Use paddingValues to avoid content being obscured by app bars
            user?.let {
                UserDetailContent(
                    user = it,
                    modifier = Modifier.padding(paddingValues)
                ) // Apply padding to the detail content
            } ?: run {
                Text(
                    "User not found",
                    Modifier.padding(paddingValues)
                ) // Show error with applied padding
            }
        }
    )
}

// Composable function that displays the user's details
@Composable
fun UserDetailContent(user: User, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) { // Apply padding to content
        Text(text = "Name: ${user.name}")
        Text(text = "Username: ${user.username}")
        Text(text = "Email: ${user.email}")
        Text(text = "Phone: ${user.phone}")
        Text(text = "Website: ${user.website}")
        Text(text = "Company: ${user.company.name}")
        Text(text = "Address: ${user.address.street}, ${user.address.city}")
    }
}

