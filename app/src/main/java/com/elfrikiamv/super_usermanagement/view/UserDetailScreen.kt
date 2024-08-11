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
import com.elfrikiamv.super_usermanagement.model.Post
import com.elfrikiamv.super_usermanagement.model.Comment
import com.elfrikiamv.super_usermanagement.viewmodel.UserViewModel
import android.util.Log

@Composable
fun UserDetailScreen(userId: Int, viewModel: UserViewModel = viewModel()) {
    val user = viewModel.users.collectAsState().value.find { it.id == userId }
    val posts = viewModel.posts.collectAsState().value.filter { it.userId == userId }
    val comments = viewModel.comments.collectAsState().value.filter { it.postId in posts.map { post -> post.id } }

    // Log para depuración
    Log.d("UserDetailScreen", "User: $user, Posts: $posts, Comments: $comments")

    Scaffold(
        topBar = { TopAppBar(title = { Text("Detalles del Usuario") }) },
        content = { paddingValues ->
            user?.let {
                UserDetailContent(
                    user = it,
                    posts = posts,
                    comments = comments,
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
    comments: List<Comment>,
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

        // Mostrar posts del usuario solo si hay posts
        if (posts.isNotEmpty()) {
            Text(text = "Publicaciones:")
            posts.forEach { post ->
                Text(text = "Título: ${post.title}")
                // Puedes descomentar la línea siguiente si también quieres mostrar el contenido del post
                // Text(text = "Contenido: ${post.body}")

                // Mostrar comentarios asociados al post solo si hay comentarios
                val postComments = comments.filter { it.postId == post.id }
                if (postComments.isNotEmpty()) {
                    Text(text = "Comentarios:")
                    postComments.forEach { comment ->
                        Text(text = "Comentario: ${comment.name}")
                        // Puedes descomentar la línea siguiente si quieres mostrar más información del comentario
                        // Text(text = "Por: ${user.name} (${user.email})")
                    }
                }
            }
        }
    }
}

