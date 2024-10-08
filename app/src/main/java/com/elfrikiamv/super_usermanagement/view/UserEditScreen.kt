package com.elfrikiamv.super_usermanagement.view

//UserEditScreen.kt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elfrikiamv.super_usermanagement.model.User
import com.elfrikiamv.super_usermanagement.navigation.Screen
import com.elfrikiamv.super_usermanagement.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserEditScreen(
    userId: Int,
    navController: NavController,
    viewModel: UserViewModel = viewModel()
) {
    // Obtenemos la lista de usuarios desde el ViewModel utilizando LiveData y observeAsState
    val users by viewModel.users.observeAsState(emptyList())
    val user = users.find { it.id == userId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Usuario") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar",
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }
            )
        },
        content = { paddingValues ->
            user?.let {
                UserEditContent(
                    user = it,
                    onSaveClick = { updatedUser ->
                        viewModel.updateUser(updatedUser)
                        // Regresar a UserDetailScreen
                        navController.navigate(Screen.UserDetail.createRoute(updatedUser.id)) {
                            popUpTo(Screen.UserDetail.route) {
                                inclusive = true
                            } // Limpia el back stack
                        }
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

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Espaciado entre los elementos
    ) {
        // Card que contiene los campos de entrada
        ElevatedCard(
            modifier = Modifier.fillMaxWidth() // La tarjeta ocupa todo el ancho
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = nameState.value,
                    onValueChange = { nameState.value = it },
                    label = { Text("Nombre:") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    label = { Text("Email:") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = phoneState.value,
                    onValueChange = { phoneState.value = it },
                    label = { Text("Teléfono:") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = websiteState.value,
                    onValueChange = { websiteState.value = it },
                    label = { Text("Sitio web:") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Botón fuera de la tarjeta, con el ancho completo
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 2.dp),
            thickness = 1.dp
        )

        Text(
            text = "Important: resource will not be really updated on the server but it will be faked as if. More info in https://jsonplaceholder.typicode.com/guide/.",
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.8f)
        )
    }
}