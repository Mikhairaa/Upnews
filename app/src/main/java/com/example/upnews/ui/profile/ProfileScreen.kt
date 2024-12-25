package com.example.upnews.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.upnews.R
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.ui.ViewModelFactory
import com.example.upnews.ui.profile.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    profileViewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(UserPreferences.getInstance(LocalContext.current))
    )
) {
    // State observables
    val profileState by profileViewModel.profileState.collectAsState()
    val isLoading by profileViewModel.isLoading.collectAsState(false)
    val errorMessage by profileViewModel.errorMessage.collectAsState()

    val scrollState = rememberScrollState()

    // Fetch profile data when the composable is first loaded
    LaunchedEffect(Unit) {
        profileViewModel.fetchProfile()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", color = Color.Black) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            // Show loading indicator
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            // Show error message if any
            errorMessage?.takeIf { it.isNotBlank() }?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Show profile data if available
            profileState?.let { profile ->
                ProfileCard(
                    navController = navController,
                    name = profile.name.orEmpty(),
                    email = profile.email.orEmpty()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Logout Card
            LogoutCard()
        }
    }
}

@Composable
fun ProfileCard(
    navController: NavHostController,
    name: String,
    email: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Merah_Hati),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Image
            Icon(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Picture",
                tint = Color.White,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // User Information
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = email,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Edit Icon
            IconButton(
                onClick = {
                    navController.navigate("updateProfile")
                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "Edit Profile",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun LogoutCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "Logout",
                tint = Merah_Hati,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Log out",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Further secure your account for safety",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.kosong),
                contentDescription = "Arrow",
                tint = Color.White, // Warna panah di ujung kanan
                modifier = Modifier.size(24.dp) // Ukuran ikon sama dengan logout
            )
            }
        }
    }

//@Preview(showBackground = true)
//@Composable
//fun ProfileScreenPreview() {
//    ProfileScreen(navController = NavHostController(LocalContext.current))
//}
