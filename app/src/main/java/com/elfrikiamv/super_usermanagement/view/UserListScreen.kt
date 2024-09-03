package com.elfrikiamv.super_usermanagement.view

// UserListScreen.kt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
    // Obtenemos la lista de usuarios desde el ViewModel utilizando LiveData y observeAsState
    val users by viewModel.users.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("User Management") }) // Barra superior con el título de la pantalla
        },
        content = { paddingValues ->
            UserList(
                users = users,
                navController = navController,
                onDeleteUser = { user -> viewModel.deleteUser(user) }, // Llamamos a la función para eliminar usuario
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}

@Composable
fun UserList(users: List<User>, navController: NavController, onDeleteUser: (User) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(users) { user ->
            UserItem(
                user = user,
                onClick = { navController.navigate(Screen.UserDetail.createRoute(user.id)) },
                onDelete = { onDeleteUser(user) } // Acción para eliminar el usuario
            )
        }
    }
}

@Composable
fun UserItem(user: User, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }, // Acción al hacer clic en la card
        elevation = 4.dp
    ) {
        // Se utiliza una fila para colocar el nombre y el ícono de eliminar en la misma línea
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween // Espaciado entre el contenido
        ) {
            // Column para los detalles del usuario
            Column(
                modifier = Modifier.weight(1f) // Ocupa todo el espacio restante
            ) {
                Text(text = user.name, style = MaterialTheme.typography.h6)
                Text(text = user.email, style = MaterialTheme.typography.body2)
            }

            // Botón de ícono para eliminar al usuario
            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar usuario") // Ícono de basura
            }
        }
    }
}