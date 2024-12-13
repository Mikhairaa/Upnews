package com.example.upnews.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.example.upnews.ui.home.HomePage
import com.example.upnews.ui.homepage.HomePage
import com.example.upnews.ui.login.LoginPage
import com.example.upnews.ui.signUp.SignupPage

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomePage(onClick = {
                navController.navigate("login") // Navigate to Login Screen
            })
        }
        composable("login") {
            LoginPage(modifier,navController)
        }
        composable("signup") {
            SignupPage(modifier,navController,authViewModel)
        }
        composable("home") {
            HomePage(modifier,navController)
        }
        composable("upload") {
            UploadPage()
        }
    }
}