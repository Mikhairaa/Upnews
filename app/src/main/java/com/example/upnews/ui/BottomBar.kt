package com.example.upnews.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.upnews.R

// Data class untuk item BottomBar dengan tambahan route
data class BottomBarItem(
    val title: String,
    val iconRes: Int,
    val route: String // Menambahkan route untuk navigasi
)

@Composable
fun BottomBar(
    navController: NavHostController // Menggunakan NavController untuk navigasi
) {
    // Mendapatkan rute yang aktif saat ini
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val bottomBarItems = listOf(
        BottomBarItem("Home", R.drawable.humz, "home"),
        BottomBarItem("Notif", R.drawable.notif, "notif"),
        BottomBarItem("Upload", R.drawable.submit, "upload"),
        BottomBarItem("My News", R.drawable.news, "myNews"),
        BottomBarItem("Profile", R.drawable.user, "profile")
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        bottomBarItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currentRoute == item.route, // Menentukan apakah item ini dipilih
                onClick = {
                    navController.navigate(item.route) {
                        // Pastikan rute sebelumnya tidak tumpang tindih
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp),
                        tint = if (currentRoute == item.route) Color.Red else Color.Unspecified
                    )
                },
                label = { Text(item.title) }
            )
        }
    }
}
