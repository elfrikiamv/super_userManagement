package com.elfrikiamv.super_usermanagement.view

// UserDetailScreen.kt

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elfrikiamv.super_usermanagement.model.Post
import com.elfrikiamv.super_usermanagement.model.User
import com.elfrikiamv.super_usermanagement.navigation.Screen
import com.elfrikiamv.super_usermanagement.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userId: Int?,
    navController: NavController,
    viewModel: UserViewModel = viewModel()
) {
    val users by viewModel.users.observeAsState(emptyList())
    val user = users.find { it.id == userId }

    Log.d("UserDetailScreen", "Usuario encontrado: ${user?.name}")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del Usuario") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar",
                        modifier = Modifier.clickable {
                            navController.popBackStack() // Regresa a la pantalla anterior
                        }
                    )
                }
            )
        },
        content = { paddingValues ->
            user?.let {
                UserDetailContent(
                    user = it,
                    posts = viewModel.posts.observeAsState(emptyList()).value.filter { post -> post.userId == userId },
                    navController = navController,
                    modifier = Modifier.padding(paddingValues)
                )
            } ?: run {
                Text("Usuario no encontrado", Modifier.padding(paddingValues))
            }
        }
    )
}

@Composable
fun UserDetailContent(
    user: User,
    posts: List<Post>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Nombre: ${user.name}")
        Text(text = "Nombre de usuario: ${user.username}")
        Text(text = "Correo electrónico: ${user.email}")
        Text(text = "Teléfono: ${user.phone}")
        Text(text = "Sitio web: ${user.website}")
        Text(text = "Empresa: ${user.company.name}")
        Text(text = "Dirección: ${user.address.street}, ${user.address.city}")

        Button(
            onClick = {
                navController.navigate(Screen.UserEdit.createRoute(user.id))
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Editar Usuario")
        }

        Text(text = "Publicaciones:")

        LazyColumn {
            items(posts) { post ->
                PostCard(post = post) {
                    navController.navigate(Screen.Comments.createRoute(post.id))
                }
            }
        }
    }
}

@Composable
fun PostCard(post: Post, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        // Si deseas aplicar una elevación en Material 3, puedes usar este modificador
        elevation = CardDefaults.elevatedCardElevation(4.dp), // Aplica la elevación de la sombra
        // También puedes controlar el color de la sombra
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Título: ${post.title}")
            Text(text = "Contenido: ${post.body}")
        }
    }
}