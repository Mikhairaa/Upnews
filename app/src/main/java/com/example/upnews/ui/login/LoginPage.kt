package com.example.upnews.ui.login

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.upnews.R
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.ui.ViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    loginViewModel:LoginViewModel= viewModel(factory = ViewModelFactory(UserPreferences.getInstance(LocalContext.current)))
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val loginResult by loginViewModel.loginResult.observeAsState()
    val isLoading by loginViewModel.isLoading.observeAsState(false)
    BackHandler { navController.popBackStack() }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        // Logo
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(id = R.drawable.upnews),
            contentDescription = "App Logo",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))

        // Title
        Text(
            text = stringResource(id = R.string.welcome),
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        )
        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = stringResource(id = R.string.login_account),
            style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Email Input
        OutlinedTextField(value = email, onValueChange = {
            email = it
        }, label = {
            Text(
                text = stringResource(id = R.string.email_label),
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )
        },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,               // Warna garis batas saat difokuskan
                unfocusedBorderColor = Color.Gray,             // Warna garis batas saat tidak difokuskan
                focusedLabelColor = Color.Black,               // Warna label ketika difokuskan
                unfocusedLabelColor = Color.Gray,              // Warna label ketika tidak difokuskan
                cursorColor = Color.Black                      // Warna kursor
            ))
        Spacer(modifier = Modifier.height(15.dp))

        // Password Input
        OutlinedTextField(value = password, onValueChange = {
            password = it
        }, label = {
            Text(
                text = stringResource(id = R.string.password_label),
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth())
        },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,               // Warna garis batas saat difokuskan
                unfocusedBorderColor = Color.Gray,             // Warna garis batas saat tidak difokuskan
                focusedLabelColor = Color.Black,               // Warna label ketika difokuskan
                unfocusedLabelColor = Color.Gray,              // Warna label ketika tidak difokuskan
                cursorColor = Color.Black                      // Warna kursor
            )
        )

// Forgot Password Row
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

            }
            TextButton (onClick = { /* Handle Forgot Password */})
            {
                Text(
                    text = stringResource(id = R.string.forgot_password),
                    color = colorResource(id = R.color.merah),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

// Sign In   Button
        Button(
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.merah)),
            onClick = {
                loginViewModel.login(email, password) },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Text(
                text = if (isLoading) "Loading..." else "Sign In",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }

        TextButton(onClick ={
            navController.navigate("signup")
        })
        {
            Text(
                text = stringResource(id = R.string.sign_up),
                color = colorResource(id = R.color.merah),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp)
        }
    }
    // Observasi hasil login
    loginResult?.let { result ->
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(LocalContext.current, "Email dan password wajib diisi.", Toast.LENGTH_SHORT).show()
        } else {
            result.onSuccess {
                // Arahkan ke halaman Home jika login berhasil
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
            result.onFailure { exception ->
                // Periksa pesan kesalahan spesifik
                val errorMessage = exception.message ?: "Terjadi kesalahan"
                if (errorMessage.contains("Incorrect email or password", true)) {
                    Toast.makeText(LocalContext.current, "Email atau password salah.", Toast.LENGTH_SHORT).show()
                } else {
                    // Tampilkan pesan kesalahan umum
                    Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPagePreview() {
    LoginPage(navController = NavHostController(LocalContext.current))
}