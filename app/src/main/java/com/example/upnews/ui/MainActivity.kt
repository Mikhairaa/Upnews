package com.example.upnews.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.upnews.data.local.UserPreferences
import kotlinx.coroutines.flow.Flow

class  MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userPreferences = UserPreferences.getInstance(this)
        val isUserLoggedIn: Flow<Boolean> = userPreferences.isUserLoggedIn()
        setContent {
            Scaffold (modifier = Modifier.fillMaxSize()){ innerPadding ->
                MyAppNavigation(modifier = Modifier.padding(innerPadding),isUserLoggedIn = isUserLoggedIn)
            }
        }
    }
}
