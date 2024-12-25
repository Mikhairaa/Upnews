package com.example.upnews.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.upnews.R
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.ui.ViewModelFactory
import com.example.upnews.ui.profile.ProfileViewModel
import com.example.upnews.ui.profile.UpdateProfilViewModel
import com.example.upnews.ui.theme.UpnewsTheme


val Merah_Hati = Color(0xFFB80C09) // Warna merah hati


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    updateProfileViewModel: UpdateProfilViewModel = viewModel(
        factory = ViewModelFactory(UserPreferences.getInstance(LocalContext.current))
    )
) {
    val context = LocalContext.current
    val userProfile by updateProfileViewModel.userProfile.collectAsState()
    val isLoading by updateProfileViewModel.isLoading.collectAsState()
    val errorMessage by updateProfileViewModel.errorMessage.collectAsState()

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val alamat = remember { mutableStateOf("") }

    val isEditable = remember { mutableStateOf(false) }

    // Cek apakah userProfile sudah ada atau belum
    val isUserProfileLoaded = userProfile?.user != null

    LaunchedEffect(Unit) {
        updateProfileViewModel.getUserProfile()
    }

    LaunchedEffect(userProfile) {
        if (userProfile?.user != null) {
            name.value = userProfile!!.user?.name ?: ""
            email.value = userProfile!!.user?.email ?: ""
            alamat.value = userProfile!!.user?.alamat ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Update Profile",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "Back",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isEditable.value = !isEditable.value
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit),
                            contentDescription = "Edit Profile",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.LightGray, CircleShape)
                    .padding(2.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (isUserProfileLoaded) {
                Text(
                    text = userProfile!!.user?.name ?: "No name",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = userProfile!!.user?.email ?: "No email",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            } else {
                Text(
                    text = "Loading...",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fields (only enabled when the profile is loaded)
            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                placeholder = { Text("What's your name?") },
                modifier = Modifier.fillMaxWidth(),
                enabled = isEditable.value && isUserProfileLoaded,  // Only editable when profile is loaded
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Merah_Hati,
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                placeholder = { Text("your@gmail.com") },
                modifier = Modifier.fillMaxWidth(),
                enabled = isEditable.value && isUserProfileLoaded,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Merah_Hati,
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = alamat.value,
                onValueChange = { alamat.value = it },
                placeholder = { Text("Your address") },
                modifier = Modifier.fillMaxWidth(),
                enabled = isEditable.value && isUserProfileLoaded,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Merah_Hati,
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Update Button
            Button(
                onClick = {
                    if (isEditable.value) {
                        val updatedName = name.value
                        val updatedEmail = email.value
                        val updatedAlamat = alamat.value

                        updateProfileViewModel.updateProfil(updatedName, updatedEmail, updatedAlamat)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Merah_Hati),
                enabled = isEditable.value && isUserProfileLoaded
            ) {
                Text(
                    text = "Update Profile",
                    style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                )
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// Light Mode Preview
//@Preview(showBackground = true)
//@Composable
//fun UpdateProfileScreenPreview() {
//    UpnewsTheme {
//        UpdateProfileScreen()
//    }
//}

