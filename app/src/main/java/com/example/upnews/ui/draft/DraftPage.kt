package com.example.app.view.upload

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.upnews.R
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.data.retrofit.ApiService
import com.example.upnews.ui.ViewModelFactory
import com.example.upnews.ui.draft.DraftViewModel
import com.example.upnews.ui.login.LoginViewModel


val CustomRed = Color(0xFFB80C09)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DraftPage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    draftViewModel: DraftViewModel = viewModel(
        factory = ViewModelFactory(UserPreferences.getInstance(LocalContext.current))),

) {
    // Ambil data drafts, isLoading, dan errorMessage dari ViewModel
    val drafts by draftViewModel.drafts.collectAsState(emptyList())
    val isLoading by draftViewModel.isLoading.collectAsState(false)
    val errorMessage by draftViewModel.errorMessage.collectAsState("")

    val scrollState = rememberScrollState()

    // Memanggil fetchDrafts() ketika halaman pertama kali dimuat
    LaunchedEffect(Unit) {
        draftViewModel.fetchDrafts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 120.dp, start = 25.dp, end = 25.dp)
    ) {
        // Jika sedang loading, tampilkan loading indicator
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // Jika ada error, tampilkan pesan error
        errorMessage?.takeIf { it.isNotBlank() }?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }
        // Jika data drafts ada, tampilkan daftar draft
        if (drafts.isNotEmpty()) {
            drafts.forEach { draft ->
                Log.d("DraftPage", "Draft: ${draft.judul}")
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
                            .padding(bottom = 7.dp)
                            .height(180.dp)
                    ) {
                        // Upload News Header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(CustomRed)
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Draft",
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = CustomRed,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = draft.updatedAt ?: " No Date",
                                    color = Color.White,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        // Content Section
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Title: ${draft.judul}", fontSize = 15.sp)
                            Text(text = "Date: ${draft.tanggal}", fontSize = 15.sp)
                            Text(text = "Time: ${draft.waktu}", fontSize = 15.sp)
                            Text(text = "Location: ${draft.lokasi}", fontSize = 15.sp)
                        }

                        // Buttons Section
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                                .height(40.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Spacer(modifier = Modifier.width(30.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                            ) {
                                // Tombol Hapus dengan Ikon
                                IconButton(
                                    modifier = Modifier
                                        .size(20.dp), // This adjusts the size of the IconButton itself
                                    onClick = {
                                        // Action for the delete button
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.delete),
                                        contentDescription = "Hapus",
                                        tint = colorResource(id = R.color.grey),
                                        modifier = Modifier.size(20.dp) // This adjusts the size of the icon inside the button
                                    )
                                }

                                Spacer(modifier = Modifier.width(17.dp))

                                // Tombol Edit dengan Ikon
                                IconButton(
                                    modifier = Modifier.size(20.dp),
                                    onClick = {
                                        // Aksi untuk tombol edit
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.edit), // Ikon dari res/drawable
                                        contentDescription = "Edit",
                                        tint = colorResource(id = R.color.grey),
                                        modifier = Modifier.size(20.dp)
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



//@Preview(showBackground = true)
//@Composable
//fun PreviewDraftScreen() {
//    DraftPage()
//}
