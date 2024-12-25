package com.example.upnews.ui.draft

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.upnews.R


@Composable
fun DraftButton(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(),
        horizontalArrangement = Arrangement.End
    ) {
        Spacer(modifier = Modifier.width(220.dp))
        // Tombol Hapus dengan Ikon
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier
                .size(40.dp), // Ukuran tombol yang lebih kecil dan proporsional

        ) {
            Icon(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = "Hapus",
                tint = colorResource(id = R.color.grey),
                modifier = Modifier.size(24.dp) // Ukuran ikon di dalam tombol
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        // Tombol Edit dengan Ikon
        IconButton(
            onClick = onEditClick,
            modifier = Modifier.size(40.dp), // Ukuran tombol yang lebih kecil dan proporsional

        ) {
            Icon(
                painter = painterResource(id = R.drawable.edit),
                contentDescription = "Edit",
                tint = colorResource(id = R.color.grey),
                modifier = Modifier.size(24.dp) // Ukuran ikon di dalam tombol
            )
        }
    }
}