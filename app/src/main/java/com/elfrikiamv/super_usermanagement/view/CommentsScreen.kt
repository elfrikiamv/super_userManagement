package com.elfrikiamv.super_usermanagement.view

// CommentsScreen.kt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.elfrikiamv.super_usermanagement.model.Comment
import com.elfrikiamv.super_usermanagement.viewmodel.UserViewModel

@Composable
fun CommentsScreen(postId: Int, viewModel: UserViewModel = viewModel()) {
    // Cambiar a observeAsState() para LiveData
    val comments =
        viewModel.comments.observeAsState(emptyList()).value.filter { it.postId == postId }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Comentarios") }) },
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
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Comentario: ${comment.name}")
            Text(text = "Por: ${comment.email}")
            Text(text = "Contenido: ${comment.body}")
        }
    }
}