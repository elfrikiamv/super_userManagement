package com.elfrikiamv.super_usermanagement.view

// UserDetailScreen.kt

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable {
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
    Column(modifier = modifier.padding(horizontal = 16.dp)) {

        // Encapsulamos la información del usuario en una Card
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp) // Padding alrededor de la Card
        ) {
            // Contenido dentro de la Card
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Nombre: ${user.name}")
                Text(text = "Nombre de usuario: ${user.username}")
                Text(text = "Correo electrónico: ${user.email}")
                Text(text = "Teléfono: ${user.phone}")
                Text(text = "Sitio web: ${user.website}")
                Text(text = "Empresa: ${user.company.name}")
                Text(text = "Dirección: ${user.address.street}, ${user.address.city}")
            }
        }

        // Botón para editar el usuario
        Button(
            onClick = {
                navController.navigate(Screen.UserEdit.createRoute(user.id))
            },
            modifier = Modifier
                //.padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Text("Editar Usuario")
        }

        // Agregamos un Divider entre el botón y el texto "Publicaciones"
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = 1.dp,
            //color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )

        // Título para las publicaciones
        Text(
            text = "Publicaciones de ${user.username}:",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista de publicaciones del usuario
        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            //.padding(top = 16.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Título: ${post.title}")
            Text(text = "Contenido: ${post.body}")
        }
    }
}