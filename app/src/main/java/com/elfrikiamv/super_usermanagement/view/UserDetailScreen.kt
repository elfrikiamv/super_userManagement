package com.elfrikiamv.super_usermanagement.view

// UserDetailScreen.kt

import android.util.Log
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontStyle
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
                Row {
                    Text(
                        text = "Nombre: ",
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .alignByBaseline()
                            .alpha(0.8f) // Aplica opacidad del 80%
                    )
                    Text(
                        text = user.name,
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Row {
                    Text(
                        text = "Usuario: ",
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .alignByBaseline()
                            .alpha(0.8f)
                    )
                    Text(
                        text = user.username,
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Row {
                    Text(
                        text = "Email: ",
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .alignByBaseline()
                            .alpha(0.8f)
                    )
                    Text(
                        text = user.email,
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Row {
                    Text(
                        text = "Teléfono: ",
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .alignByBaseline()
                            .alpha(0.8f)
                    )
                    Text(
                        text = user.phone,
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Row {
                    Text(
                        text = "Sitio web: ",
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .alignByBaseline()
                            .alpha(0.8f)
                    )
                    Text(
                        text = user.website,
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Row {
                    Text(
                        text = "Empresa: ",
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .alignByBaseline()
                            .alpha(0.8f)
                    )
                    Text(
                        text = user.company.name,
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Row {
                    Text(
                        text = "Dirección: ",
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .alignByBaseline()
                            .alpha(0.8f)
                    )
                    Text(
                        text = "${user.address.street}, ${user.address.city}",
                        modifier = Modifier.alignByBaseline()
                    )
                }
            }
        }

        // Botón para editar el usuario
        Button(
            onClick = {
                navController.navigate(Screen.UserEdit.createRoute(user.id))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Editar Usuario")
        }

        // Agregamos un Divider entre el botón y el texto "Publicaciones"
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = 1.dp
        )

        // Título para las publicaciones
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(
                text = "Publicaciones de ",
                modifier = Modifier.alignByBaseline()
            )
            Text(
                text = "${user.username}:",
                fontStyle = FontStyle.Italic,
                modifier = Modifier.alignByBaseline()
            )
        }

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
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 2.dp),
                thickness = 1.dp
            )
            Text(
                text = post.body,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.alpha(0.8f)
            )
        }
    }
}