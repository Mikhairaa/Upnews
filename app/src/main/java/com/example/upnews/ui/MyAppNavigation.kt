package com.example.upnews.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.upnews.AllNewsScreen
import com.example.upnews.ui.screens.DoneScreen
import com.example.upnews.ui.screens.OnProgressScreen
import com.example.upnews.ui.screens.UpdateProfileScreen
import com.example.upnews.ui.screens.ProfileScreen
import com.example.upnews.ui.screens.RejectedScreen
import kotlinx.coroutines.flow.Flow

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, isUserLoggedIn: Flow<Boolean>) {
    val navController = rememberNavController()
    val isUserLoggedIn = isUserLoggedIn.collectAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = if (isUserLoggedIn.value)"login" else "signup") {

        composable("upload") {
            UploadPage(modifier,navController)
        }
        composable("profile") {
            ProfileScreen(modifier,navController)
        }
        composable("updateProfile") {
            UpdateProfileScreen(modifier,navController)
        }
        composable("onProgress") {
            OnProgressScreen(modifier,navController)
        }
        composable("myNews") {
            MyNewsNavigation(modifier,navController)
        }
        composable("rejected") {
            RejectedScreen(modifier,navController)
        }
        composable("done") {
            DoneScreen(modifier,navController)
        }
        composable("all") {
            AllNewsScreen(modifier,navController)
        }
    }
}