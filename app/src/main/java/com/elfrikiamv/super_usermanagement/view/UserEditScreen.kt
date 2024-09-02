package com.elfrikiamv.super_usermanagement.view

//UserEditScreen.kt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elfrikiamv.super_usermanagement.model.User
import com.elfrikiamv.super_usermanagement.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserEditScreen(
    userId: Int,
    navController: NavController,
    viewModel: UserViewModel = viewModel()
) {
    val user = viewModel.users.observeAsState().value?.find { it.id == userId }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Editar Usuario") }) },
        content = { paddingValues ->
            user?.let {
                UserEditContent(
                    user = it,
                    onSaveClick = { updatedUser ->
                        viewModel.updateUser(updatedUser)
                        navController.popBackStack() // Volver a la pantalla anterior después de guardar
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            } ?: run {
                Text("Usuario no encontrado", Modifier.padding(paddingValues))
            }
        }
    )
}

@Composable
fun UserEditContent(
    user: User,
    onSaveClick: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    val nameState = remember { mutableStateOf(user.name) }
    val emailState = remember { mutableStateOf(user.email) }
    val phoneState = remember { mutableStateOf(user.phone) }
    val websiteState = remember { mutableStateOf(user.website) }

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phoneState.value,
            onValueChange = { phoneState.value = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = websiteState.value,
            onValueChange = { websiteState.value = it },
            label = { Text("Sitio web") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val updatedUser = user.copy(
                    name = nameState.value,
                    email = emailState.value,
                    phone = phoneState.value,
                    website = websiteState.value
                )
                onSaveClick(updatedUser)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Guardar")
        }
    }
}
