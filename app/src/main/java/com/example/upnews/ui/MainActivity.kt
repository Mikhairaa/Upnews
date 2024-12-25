package com.example.upnews.ui

import MyAppNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authViewModel: AuthViewModel by viewModels()

        setContent {
            // Inisialisasi NavController
            val navController = rememberNavController()

            // Scaffold sebagai wadah konten dengan padding
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                // Memanggil MyAppNavigation dan menyertakan navController dan padding
                MyAppNavigation(
                    modifier = Modifier.padding(innerPadding),
                    authViewModel = authViewModel
                )
            }
        }
    }
}
