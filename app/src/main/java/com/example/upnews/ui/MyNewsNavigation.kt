package com.example.upnews.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.upnews.AllNewsScreen
import com.example.upnews.CustomRed
import com.example.upnews.ui.screens.DoneScreen
import com.example.upnews.ui.screens.OnProgressScreen
import com.example.upnews.ui.screens.RejectedScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNewsNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("All", "On Progress", "Rejected", "Done")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "My News",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = CustomRed
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            BottomBar(navController = navController) // Tambahkan BottomBar di sini
        }
        ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Tab Row
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = CustomRed,
                modifier = Modifier.fillMaxWidth(),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTab])
                            .height(4.dp)
                            .background(color = CustomRed),
                        color = CustomRed
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontSize = 14.sp) },
                        selectedContentColor = CustomRed,
                        unselectedContentColor = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display News Cards
            when (selectedTab) {
                0 -> AllNewsScreen(modifier = modifier, navController = navController)
                1 -> OnProgressScreen(modifier = modifier, navController = navController)
                2 -> RejectedScreen(modifier = modifier, navController = navController)
                3 -> DoneScreen(modifier = modifier, navController = navController)
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MyNewsScreenPreview() {
//    val testNavController = TestNavHostController(LocalContext.current)
//    MyNewsNavigation(navController = testNavController)
//}


