package com.elfrikiamv.super_usermanagement.view

// UserListScreen.kt

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elfrikiamv.super_usermanagement.model.User
import com.elfrikiamv.super_usermanagement.navigation.Screen
import com.elfrikiamv.super_usermanagement.viewmodel.UserViewModel

// Composable function to display the list of users
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    val users by viewModel.users.observeAsState(emptyList())
    val searchQuery =
        remember { mutableStateOf(TextFieldValue("")) } // Estado para el campo de búsqueda
    val isSearchActive =
        remember { mutableStateOf(false) } // Estado para mostrar u ocultar el campo de búsqueda

    // Filtrar usuarios según la búsqueda por nombre o correo electrónico
    val filteredUsers = users.filter {
        it.name.contains(searchQuery.value.text, ignoreCase = true) ||
                it.email.contains(searchQuery.value.text, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (!isSearchActive.value) {
                        Text("User Management")
                    }
                },
                actions = {
                    // Icono de lupa que activa el campo de búsqueda
                    AnimatedVisibility(
                        visible = isSearchActive.value,
                        enter = androidx.compose.animation.expandHorizontally(
                            animationSpec = tween(
                                300
                            )
                        ),
                        exit = androidx.compose.animation.shrinkHorizontally(
                            animationSpec = tween(
                                300
                            )
                        )
                    ) {
                        OutlinedTextField(
                            value = searchQuery.value,
                            onValueChange = { searchQuery.value = it },
                            placeholder = { Text("Buscar por nombre o email") },
                            modifier = Modifier.fillMaxWidth(0.85f),
                            singleLine = true
                        )
                    }

                    IconButton(
                        onClick = {
                            isSearchActive.value = !isSearchActive.value
                            if (!isSearchActive.value) searchQuery.value =
                                TextFieldValue("") // Limpia el texto cuando se cierra
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar usuario"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            UserList(
                users = filteredUsers, // Usamos la lista filtrada
                navController = navController,
                onDeleteUser = { user -> viewModel.deleteUser(user) },
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}

@Composable
fun UserList(
    users: List<User>,
    navController: NavController,
    onDeleteUser: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, // Acción al hacer clic en la card
    ) {
        // Se utiliza una fila para colocar el nombre y el ícono de eliminar en la misma línea
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween // Espaciado entre el contenido
        ) {
            // Column para los detalles del usuario
            Column(
                modifier = Modifier.weight(1f) // Ocupa all el espacio restante
            ) {
                Text(
                    text = "${user.name}.",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = user.email,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.alpha(0.8f) // Aplica opacidad del 80%
                )
            }

            // Botón de ícono para eliminar al usuario
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar usuario"
                ) // Ícono de basura
            }
        }
    }
}