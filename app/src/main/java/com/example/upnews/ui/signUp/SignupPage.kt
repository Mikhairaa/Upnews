package com.example.upnews.ui.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.upnews.ui.AuthViewModel
import com.example.upnews.R
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupPage(modifier: Modifier = Modifier, navController: NavHostController, authViewModel: AuthViewModel) {
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fotoProfil by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        // Title
        Image(
            painter = painterResource(id = R.drawable.upnews),
            contentDescription = "App Logo",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = stringResource(id = R.string.sign_up_label),
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        )
        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = stringResource(id = R.string.create_account_label),
            style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Name Input
        OutlinedTextField(value = nama, onValueChange = {
            nama = it
        }, label = {
            Text(
                text = stringResource(id = R.string.name_label),
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
            )
        )
        Spacer(modifier = Modifier.height(15.dp))

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
            )
        )
        Spacer(modifier = Modifier.height(15.dp))

        // Address Input
        OutlinedTextField(value = alamat, onValueChange = {
            alamat = it
        }, label = {
            Text(
                text = stringResource(id = R.string.address_label),
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
            )
        )
        Spacer(modifier = Modifier.height(15.dp))

        // Password Input
        OutlinedTextField(value = password, onValueChange = {
            password = it
        }, label = {
            Text(
                text = stringResource(id = R.string.password_label),
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )
        },
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,               // Warna garis batas saat difokuskan
                unfocusedBorderColor = Color.Gray,             // Warna garis batas saat tidak difokuskan
                focusedLabelColor = Color.Black,               // Warna label ketika difokuskan
                unfocusedLabelColor = Color.Gray,              // Warna label ketika tidak difokuskan
                cursorColor = Color.Black                      // Warna kursor
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

// Create Button
        Button(
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.merah)),
            onClick = {
            },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = stringResource(id = R.string.create_account),
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 16.sp
            )
        }

// Create TextButton for "Back to Login"
        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text(
                text = stringResource(id = R.string.back_to_login),
                color = colorResource(id = R.color.merah),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignupPagePreview() {
    val mockNavController = rememberNavController() // Mock NavController for preview
    val mockAuthViewModel = AuthViewModel() // Instantiate a mock AuthViewModel if possible
    SignupPage(
        modifier = Modifier,
        navController = mockNavController,
        authViewModel = mockAuthViewModel
    )
}