package com.example.upnews.ui.forgotPw

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.PasswordVisualTransformation
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.upnews.R
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.ui.ViewModelFactory

// Fungsi ekstensi untuk menampilkan Toast
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun ForgotPw(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    changePasswordViewModel: ChangePasswordViewModel = viewModel(factory = ViewModelFactory(UserPreferences.getInstance(LocalContext.current)))
) {
    // States for handling UI
    var passwordLama by remember { mutableStateOf("") }
    var passwordBaru by remember { mutableStateOf("") }
    var konfirmasiPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    val changePasswordResult by changePasswordViewModel.changePasswordResult.observeAsState("")
    val isLoadingState by changePasswordViewModel.isLoading.observeAsState(false)

    // Menangani hasil perubahan password dengan LaunchedEffect
    LaunchedEffect(changePasswordResult) {
        when (changePasswordResult) {
            "Password berhasil diubah" -> {
                // Berhasil mengganti password
                context.showToast("Password berhasil diubah")
                navController.navigate("login")
            }
            "Semua kolom harus diisi" -> {
                context.showToast("Semua kolom harus diisi")
            }
            "Password baru dan konfirmasi password tidak cocok" -> {
                context.showToast("Password baru dan konfirmasi password tidak cocok")
            }
            "Token tidak ditemukan" -> {
                context.showToast("Token tidak ditemukan")
            }
            "Response kosong, gagal mengganti password" -> {
                context.showToast("Response kosong, gagal mengganti password")
            }
            "Gagal mengganti password" -> {
                context.showToast("Gagal mengganti password")
            }
            else -> {
                // Jika ada error lain
                context.showToast("Terjadi kesalahan: $changePasswordResult")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            IconButton(onClick = { navController.navigate("login") }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(26.dp),
                    tint = colorResource(id = R.color.custom_red)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(id = R.drawable.upnews),
            contentDescription = "Up News Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "Change Password",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Input Fields
        OutlinedTextField(
            value = passwordLama,
            onValueChange = { passwordLama = it },
            label = { Text("Old Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        OutlinedTextField(
            value = passwordBaru,
            onValueChange = { passwordBaru = it },
            label = { Text("New Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = konfirmasiPassword,
            onValueChange = { konfirmasiPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                // Menyelesaikan input saat tekan 'Done'
            })
        )

        // Menampilkan error message jika ada
        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = Color.Red,
                style = TextStyle(fontSize = 12.sp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Button untuk mengganti password
        Button(
            onClick = {
                // Memanggil ViewModel untuk mengganti password
                isLoading = true
                changePasswordViewModel.changePassword(passwordLama, passwordBaru, konfirmasiPassword)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.custom_red)
            ),
            enabled = !isLoadingState // Menonaktifkan tombol saat sedang memproses
        ) {
            if (isLoadingState) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text(text = "CHANGE PASSWORD", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(200.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewForgotPw() {
    ForgotPw()
}
