package com.elfrikiamv.super_usermanagement.view

// UserListScreen.kt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elfrikiamv.super_usermanagement.model.User
import com.elfrikiamv.super_usermanagement.viewmodel.UserViewModel

// Composable function to display the list of users
@Composable
fun UserListScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    val users by viewModel.users.collectAsState() // Observe the users from the ViewModel

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("User Management") }) // Top app bar with title
        },
        content = { paddingValues -> // Use paddingValues to avoid content being obscured by app bars
            UserList(users, navController, modifier = Modifier.padding(paddingValues)) // Pass the list of users to the UserList composable with padding
        }
    )
}

// Composable function that displays the list of users in a LazyColumn
@Composable
fun UserList(users: List<User>, navController: NavController, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier, // Apply padding to the LazyColumn
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(users) { user ->
            UserItem(user) {
                // Navigate to the UserDetailScreen when a user item is clicked
                navController.navigate("userDetail/${user.id}")
            }
        }
    }
}

// Composable function that represents a single user item in the list
@Composable
fun UserItem(user: User, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }, // Handle click event to navigate to the detail screen
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = user.name, style = MaterialTheme.typography.h6) // Display user's name
            Text(text = user.email, style = MaterialTheme.typography.body2) // Display user's email
        }
    }
}
