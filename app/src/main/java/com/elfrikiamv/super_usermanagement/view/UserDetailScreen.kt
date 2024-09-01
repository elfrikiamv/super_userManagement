package com.elfrikiamv.super_usermanagement.view

// UserDetailScreen.kt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elfrikiamv.super_usermanagement.model.Post
import com.elfrikiamv.super_usermanagement.model.User
import com.elfrikiamv.super_usermanagement.navigation.Screen
import com.elfrikiamv.super_usermanagement.viewmodel.UserViewModel

@Composable
fun UserDetailScreen(
    userId: Int?,
    navController: NavController,
    viewModel: UserViewModel = viewModel()
) {
    val user = viewModel.users.collectAsState().value.find { it.id == userId }
    val posts = viewModel.posts.collectAsState().value.filter { it.userId == userId }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Detalles del Usuario") }) },
        content = { paddingValues ->
            user?.let {
                UserDetailContent(
                    user = it,
                    posts = posts,
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
        // Mostrar detalles del usuario
        Text(text = "Nombre: ${user.name}")
        Text(text = "Nombre de usuario: ${user.username}")
        Text(text = "Correo electrónico: ${user.email}")
        Text(text = "Teléfono: ${user.phone}")
        Text(text = "Sitio web: ${user.website}")
        Text(text = "Empresa: ${user.company.name}")
        Text(text = "Dirección: ${user.address.street}, ${user.address.city}")

        // Mostrar posts del usuario en tarjetas
        Text(text = "Publicaciones:")

        LazyColumn {
            items(posts) { post ->
                PostCard(post = post) {
                    // Navegar a la pantalla de comentarios al hacer clic en la tarjeta
                    navController.navigate(Screen.Comments.createRoute(post.id))
                }
            }
        }
    }

    // Botón para editar usuario
    Button(
        onClick = {
            navController.navigate(Screen.UserEdit.createRoute(user.id))
        //navController.navigate(Screen.UserEdit.route + "/${user.id}")
        },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text("Editar Usuario")
    }
}

@Composable
fun PostCard(post: Post, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Título: ${post.title}")
            Text(text = "Contenido: ${post.body}")
        }
    }
}
