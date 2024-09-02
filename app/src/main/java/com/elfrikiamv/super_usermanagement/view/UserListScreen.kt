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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elfrikiamv.super_usermanagement.model.User
import com.elfrikiamv.super_usermanagement.navigation.Screen
import com.elfrikiamv.super_usermanagement.viewmodel.UserViewModel

// Composable function to display the list of users
@Composable
fun UserListScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    // Cambiar a observeAsState() para LiveData
    val users by viewModel.users.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("User Management") })
        },
        content = { paddingValues ->
            UserList(
                users = users,
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}

@Composable
fun UserList(users: List<User>, navController: NavController, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(users) { user ->
            UserItem(user) {
                navController.navigate(Screen.UserDetail.createRoute(user.id))
            }
        }
    }
}

@Composable
fun UserItem(user: User, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = user.name, style = MaterialTheme.typography.h6)
            Text(text = user.email, style = MaterialTheme.typography.body2)
        }
    }
}