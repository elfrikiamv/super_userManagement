package com.elfrikiamv.super_usermanagement.view

// CommentsScreen.kt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elfrikiamv.super_usermanagement.model.Comment
import com.elfrikiamv.super_usermanagement.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreen(
    postId: Int,
    navController: NavController,
    viewModel: UserViewModel = viewModel()
) {
    // Cambiar a observeAsState() para LiveData
    val comments =
        viewModel.comments.observeAsState(emptyList()).value.filter { it.postId == postId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Commenstarios") },
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
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(comments) { comment ->
                    CommentCard(comment)
                }
            }
        }
    )
}

@Composable
fun CommentCard(comment: Comment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        // Si deseas aplicar una elevación en Material 3, puedes usar este modificador
        elevation = CardDefaults.elevatedCardElevation(4.dp), // Aplica la elevación de la sombra
        // También puedes controlar el color de la sombra
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Comentario: ${comment.name}")
            Text(text = "Por: ${comment.email}")
            Text(text = "Contenido: ${comment.body}")
        }
    }
}