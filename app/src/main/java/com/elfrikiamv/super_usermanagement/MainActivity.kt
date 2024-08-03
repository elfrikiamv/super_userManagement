package com.elfrikiamv.super_usermanagement

// MainActivity.kt
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.elfrikiamv.super_usermanagement.ui.theme.Super_userManagementTheme
import com.elfrikiamv.super_usermanagement.view.UserListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Super_userManagementTheme {
                UserListScreen()
            }
        }
    }
}