package com.example.upnews.ui.notif

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import com.example.upnews.R

data class NotificationItem(
    val title: String,
    val date: String,
    val description: String,
    val link: String
)
@Composable
fun NotificationCard(notification: NotificationItem) {
    var isChecked by remember { mutableStateOf(false) } // Menyimpan status checkbox

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White) // Card with white background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Info, // Menggunakan ikon Info dari Material Icons
                    contentDescription = "Info",
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFFB80C09) // Mengganti warna ikon dengan warna #B80C09
                )
                Spacer(modifier  = Modifier.width(8.dp))
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = notification.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = notification.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                // This places the checkbox on the right side of the row
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun NotificationsList(onBackClick: () -> Unit = {}) {
    val notifications = listOf(
        NotificationItem(
            title = "Berita Anda yang berjudul Fakta Terbaru Pemilik Akun Fufufafa diterima.",
            date = "11 Okt 2024, 08:00 WIB",
            description = "Imbalan telah dikirimkan, silahkan cek email Anda.",
            link = "email"
        ),
        NotificationItem(
            title = "Berita Anda yang berjudul Gunung Lewotobi Kembali Meletus.",
            date = "8 Nov 2024, 16:00 WIB",
            description = "Silakan periksa ketentuan pengajuan atau kirimkan berita lain yang sesuai.",
            link = "link"
        )
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFFB80C09)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Notification",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start
                ),
                color = colorResource(id = R.color.custom_red),
                modifier = Modifier.weight(1f) // Mengisi ruang kosong untuk memusatkan judul
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        notifications.forEach { notification ->
            NotificationCard(notification = notification)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsListPreview() {
    NotificationsList(onBackClick = { /* Tambahkan aksi navigasi kembali di sini */ })
}
