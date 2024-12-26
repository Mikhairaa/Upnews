package com.example.upnews.ui.homepage

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.upnews.R
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.ui.ViewModelFactory
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.example.upnews.ui.BottomBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = viewModel(factory = ViewModelFactory(UserPreferences.getInstance(LocalContext.current)))
) {
    val userName by homeViewModel.userName.collectAsState()
    val beritaList by homeViewModel.beritaList.collectAsState(emptyList())
    val isLoading by homeViewModel.isLoading.collectAsState(false)
    val errorMessage by homeViewModel.errorMessage.collectAsState()

    LaunchedEffect(key1 = true) {
        homeViewModel.fetchHomepageData()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(navController = navController) // Tambahkan BottomBar di sini
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "Unknown error occurred",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.upnews),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Hi, ${userName ?: "Guest"}👋",
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                            color = Color.Black
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(colorResource(id = R.color.custom_red)),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = "Create Your News\nand Get Reward",
                                        color = Color.White,
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 23.sp
                                    )
                                }
                                Image(
                                    painter = painterResource(id = R.drawable.gnews),
                                    contentDescription = "News Icon",
                                    modifier = Modifier
                                        .size(110.dp)
                                        .padding(start = 16.dp)
                                )
                            }
                        }
                    }

                    Image(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = "Plus Icon",
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.CenterHorizontally)
                            .offset(y = (-32).dp)
                            .clickable {
                                // Navigasi ke halaman upload
                                navController.navigate("upload") // Ganti dengan route halaman upload yang sesuai
                            }
                    )
                    Spacer(modifier = Modifier.height(3.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier
                                .width(310.dp)
                                .padding(bottom = 16.dp)
                        )
                        Text(
                            text = "Recently",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            ),
                            color = colorResource(id = R.color.custom_red),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        beritaList.takeLast(2).forEach { berita ->
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(colorResource(id = R.color.custom_red))
                                            .padding(16.dp)
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "Upload News",
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "On Progress",
                                                color = Color.White,
                                                fontSize = 12.sp
                                            )
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .padding(18.dp)
                                    ) {
                                        Text(
                                            text = """
                                                Title: ${berita.judul ?: "No Title"}
                                                Date: ${berita.tanggal ?: "Unknown Date"}
                                                Time: ${berita.waktu ?: "Unknown Time"}
                                                Location: ${berita.lokasi ?: "Unknown Location"}
                                            """.trimIndent(),
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                            style = TextStyle(lineHeight = 17.sp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomepagePreview() {
    HomePage()
}
