package com.example.upnews.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.upnews.R

@Composable
fun WelcomePage(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.upnews),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(100.dp)
                .clickable(onClick = onClick),
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
