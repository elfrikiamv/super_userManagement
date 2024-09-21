package com.elfrikiamv.super_usermanagement

// MainActivity.kt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.elfrikiamv.super_usermanagement.navigation.NavGraph
import com.elfrikiamv.super_usermanagement.ui.theme.Super_userManagementTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContent {
            Super_userManagementTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
