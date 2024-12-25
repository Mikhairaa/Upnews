package com.example.upnews.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.ui.theme.Merah_Hati
import com.example.upnews.ui.theme.UpnewsTheme
import com.example.upnews.data.response.BeritaOnProgress
import com.example.upnews.ui.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnProgressScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: OnProgressViewModel = viewModel(
        factory = ViewModelFactory(UserPreferences.getInstance(LocalContext.current))
    ),
) {
    var selectedTab by remember { mutableStateOf(1) } // Default to "On Progress" tab
    val tabs = listOf("All", "On Progress", "Rejected", "Done")

    val beritaOnProgress by viewModel.beritaOnProgress.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        viewModel.fetchOnProgressBerita()
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

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (!errorMessage.isNullOrEmpty()) {
                Text(
                    text = errorMessage ?: "Error occurred",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                beritaOnProgress?.forEach { beritaOnProgress ->
                    beritaOnProgress?.let {
                        OnProgressNewsCard(it)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun OnProgressNewsCard(newsItem: BeritaOnProgress) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
            .padding(0.dp)
        ) {
            // Upload News Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Merah_Hati)
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
                Box(
                    modifier = Modifier
                        .background(
                            color = Merah_Hati,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = newsItem.status ?: "",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
            // Content Section
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Title: ${newsItem.judul}", fontSize = 14.sp)
                Text(text = "Date: ${newsItem.tanggal}", fontSize = 14.sp)
                Text(text = "Time: ${newsItem.waktu}", fontSize = 14.sp)
                Text(text = "Location: ${newsItem.lokasi}", fontSize = 14.sp)
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun OnProgressScreenPreview() {
//    UpnewsTheme {
//        OnProgressScreen()
//    }
//}
