package com.example.upnews.ui.forgotPw

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import com.example.upnews.R


@Composable
fun SetNewPasswordScreen() {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Handle back action */ }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }

        // Icon and Title
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Lock Icon",
            tint = colorResource(id = R.color.custom_red), // Using the custom red color
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Set New Password",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )
        Text(
            text = "Your new password must be different to previously used passwords.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Password Fields
        PasswordField(label = "Old Password*", value = oldPassword, onValueChange = { oldPassword = it })
        Spacer(modifier = Modifier.height(8.dp))
        PasswordField(label = "New Password*", value = newPassword, onValueChange = { newPassword = it })
        Spacer(modifier = Modifier.height(8.dp))
        PasswordField(label = "Confirm Password*", value = confirmPassword, onValueChange = { confirmPassword = it })

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Must be at list 8 characters!",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = { /* Handle submit action */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors
                (containerColor = colorResource(id = R.color.custom_red)
            )
        ) {
            Text(text = "Submit", color = Color.White)
        }
    }
}

@Composable
fun PasswordField(label: String, value: String, onValueChange: (String) -> Unit) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password"
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSetNewPasswordScreen() {
    MaterialTheme {
        SetNewPasswordScreen()
    }
}
