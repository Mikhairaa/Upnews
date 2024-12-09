package com.example.app.view.upload

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DraftPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp, start = 25.dp, end = 25.dp, bottom = 16.dp)
    ) {
        Text(
            text = "Draft List",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold))
        Spacer(modifier = Modifier.height(16.dp))

        // Sample Draft Items
        repeat(3) {
            DraftItem(title = "Draft Title $it", date = "Date: 12/12/2024")
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun DraftItem(title: String, date: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold))
            Text(text = date, style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDraftScreen() {
    DraftPage()
}
