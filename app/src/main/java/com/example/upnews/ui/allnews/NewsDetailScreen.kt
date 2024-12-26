package com.example.upnews.ui.allnews


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.upnews.data.response.BeritaAll
import com.example.upnews.data.response.GetAllResponse

@Composable
fun NewsDetailScreen(news: BeritaAll) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "News Detail",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(text = "Title: ${news.judul}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Date: ${news.tanggal}", fontSize = 14.sp)
            Text(text = "Time: ${news.waktu}", fontSize = 14.sp)
            Text(text = "Location: ${news.lokasi}", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Description:",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = news.deskripsi ?: "No description available",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
