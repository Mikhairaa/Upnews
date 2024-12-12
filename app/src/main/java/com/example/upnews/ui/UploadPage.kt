package com.example.upnews.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app.view.upload.DraftPage
import com.example.app.view.upload.FormPage
import com.example.upnews.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UploadPage(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(color = colorResource(id = R.color.white)), // White background for the header
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = stringResource(id = R.string.uploadLabel),
                    style = TextStyle(fontSize = 23.sp, fontWeight = FontWeight.ExtraBold),
                    color = colorResource(id = R.color.merah),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 10.dp) // Adjust vertical padding
                )

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                .height(4.dp)
                                .background(color = colorResource(id = R.color.merah)),
                        color = colorResource(id = R.color.merah)// Red color for active tab
                        )
                    }
                ) {
                    // Tab 1: Form
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp), // Ensures both tabs have the same height
                            contentAlignment = Alignment.Center // Centers content in the Box
                        ) {
                            Text(
                                text = stringResource(id = R.string.formLabel),
                                textAlign = TextAlign.Center,
                                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                                color = if (selectedTabIndex == 0) colorResource(id = R.color.merah) else colorResource(
                                    id = R.color.grey
                                ) // Red for selected, Gray for others
                            )

                        }
                    }

                    // Tab 2: Draft
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp), // Same height as Form tab
                            contentAlignment = Alignment.Center // Centers content in the Box
                        ) {
                            Text(
                                text = stringResource(id = R.string.draftLabel),
                                textAlign =TextAlign.Center,
                                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                                modifier = Modifier
                                    .padding(top = 12.dp),
                                color = if (selectedTabIndex == 1) colorResource(id = R.color.merah) else colorResource(
                                    id = R.color.grey
                                ) // Red for selected, Gray for others
                            )
                        }
                    }
                }
            }
        }
    ) {
        when (selectedTabIndex) {
            0 -> FormPage(modifier = modifier, navController = navController)
            1 -> DraftPage(modifier = modifier, navController = navController)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewUploadScreen() {
//    UploadPage()
//}
