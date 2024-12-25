package com.example.upnews


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.data.response.BeritaAll
import com.example.upnews.ui.ViewModelFactory
import com.example.upnews.ui.allnews.AllViewModel

val CustomRed = Color(0xFFB80C09)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllNewsScreen(
    modifier:Modifier = Modifier,
    navController: NavHostController,
    allViewModel: AllViewModel = viewModel(
        factory = ViewModelFactory(UserPreferences.getInstance(LocalContext.current))
    )
) {
    // State dari ViewModel
    val allNews by allViewModel.allNews.collectAsState()
    val isLoading by allViewModel.isLoading.collectAsState(false)
    val errorMessage by allViewModel.errorMessage.collectAsState("")

    val tabs = listOf("All", "On Progress", "Rejected", "Done")
    var selectedTab by remember { mutableStateOf(0) }

    val scrollState = rememberScrollState()

    // Panggil fetchAllNews saat pertama kali dimuat
    LaunchedEffect(Unit) {
        allViewModel.fetchAllNews()
    }

    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            when {
                // Kondisi Loading
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                // Kondisi Error
                errorMessage.isNotEmpty() -> {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                // Kondisi Data Kosong
                allNews.isEmpty() -> {
                    Text(
                        text = "No news available.",
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                // Tampilkan Data
                else -> {
                    allNews.forEach { allNews ->
                        allNews.let {
                            NewsCard(it)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewsCard(all: BeritaAll) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(0.dp)) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CustomRed)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Upload News",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = all.status ?: "",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }

            // Content
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Title: ${all.judul}", fontSize = 14.sp)
                Text(text = "Date: ${all.tanggal}", fontSize = 14.sp)
                Text(text = "Time: ${all.waktu}", fontSize = 14.sp)
                Text(text = "Location: ${all.lokasi}", fontSize = 14.sp)
            }
        }
    }
}

