package com.example.app.view.upload

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.ui.ViewModelFactory
import com.example.upnews.ui.draft.DraftButton
import com.example.upnews.ui.draft.DraftViewModel


val CustomRed = Color(0xFFB80C09)

@Composable
fun DraftPage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
   draftViewModel: DraftViewModel = viewModel(
        factory = ViewModelFactory(UserPreferences.getInstance(LocalContext.current))),
) {

    val drafts by draftViewModel.drafts.collectAsState(emptyList())
    val isLoading by draftViewModel.isLoading.collectAsState(false)
    val errorMessage by draftViewModel.errorMessage.collectAsState("")
    val deleteResult by draftViewModel.deleteResult.collectAsState(Result.success(Unit))

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        draftViewModel.fetchDrafts()
    }
    LaunchedEffect(Unit) {
        draftViewModel.resetState()
    }
    // Tangani hasil deleteResult
    LaunchedEffect(deleteResult) {
        // Pastikan hanya menangani hasil delete setelah benar-benar ada perubahan
        if (deleteResult != Result.success(Unit)) {
            deleteResult?.let { result ->
                if (result.isSuccess) {
                    Toast.makeText(context, "Draft deleted successfully!", Toast.LENGTH_SHORT).show()
                } else if (result.isFailure) {
                    val error = result.exceptionOrNull()?.message ?: "Unknown error"
                    Toast.makeText(context, "Failed to delete draft: $error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 8.dp)
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
                color = CustomRed,
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
                            .wrapContentHeight()
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
                                    text = draft.createdAt ?: " No Date",
                                    color = Color.White,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        // Content Section
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            Text(text = "Title: ${draft.judul}", fontSize = 15.sp)
                            Text(text = "Date: ${draft.tanggal}", fontSize = 15.sp)
                            Text(text = "Time: ${draft.waktu}", fontSize = 15.sp)
                            Text(text = "Location: ${draft.lokasi}", fontSize = 15.sp)

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth() // Ensure buttons take up full width
                                    .padding(8.dp),
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                Spacer(modifier = Modifier.width(10.dp))
                                DraftButton(
                                    onEditClick = {
                                        Log.d("DraftPage", "Edit button clicked for draft: ${draft.idBerita}")
                                        draft.idBerita?.let { idBerita ->
                                            Log.d("DraftPage", "Navigating to FormPage with id: $idBerita")
                                            navController.navigate("form/$idBerita")
                                        } ?: run {
                                            Log.e("DraftPage", "idBerita is null, cannot navigate to FormPage")
                                        }
                                    },
                                            onDeleteClick = {
                                        if (draft.idBerita != null) {
                                            Log.d("DraftPage", "Delete clicked for: ${draft.judul}")
                                            draftViewModel.deleteDraft(draft.idBerita)
                                        } else {
                                            Log.e("DraftPage", "idBerita is null, cannot delete")
                                        }
                                    },

                                    modifier = Modifier.wrapContentSize()
                                )
                            }

                        }
                    }
                }
            }
        } else {
//Menampilkan pesan jika tidak ada draft yang ditemukan
            Text(
                text = "No drafts available.",
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
