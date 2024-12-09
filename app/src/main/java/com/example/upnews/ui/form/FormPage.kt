package com.example.app.view.upload

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.upnews.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPage() {
    var judul by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf("") }
    var waktu by remember { mutableStateOf("") }
    var lokasi by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var bukti by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var no_hp by remember { mutableStateOf("") }
    var no_rek by remember { mutableStateOf("") }
    val banks = listOf("Mandiri", "BNI", "BRI", "BSI", "BCA")
    var isExpanded by remember { mutableStateOf(false) }
    var selectedBank by remember { mutableStateOf("")}
    var accountNumber by remember { mutableStateOf("") }
    var isConfirmed by remember { mutableStateOf(false) }

    // Tambahkan Scroll State
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // Menambahkan scroll
            .padding(top = 120.dp, start = 25.dp, end = 25.dp)

      ) {
        Text(
            text = stringResource(id = R.string.form_tittle),
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = stringResource(id = R.string.sub_tittle_label),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.tittle_label),
            modifier = Modifier.height(25.dp),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        )
        // judul Input
        OutlinedTextField(
            value = judul,
            onValueChange = { judul = it },
            label = {
                Text(
                    text = stringResource(id = R.string.input_tittle_label),
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                )

            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,               // Warna garis batas saat difokuskan
                unfocusedBorderColor = Color.Gray,             // Warna garis batas saat tidak difokuskan
                focusedLabelColor = Color.Black,               // Warna label ketika difokuskan
                unfocusedLabelColor = Color.Gray,              // Warna label ketika tidak difokuskan
                cursorColor = Color.Black                      // Warna kursor
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.date_label),
            modifier = Modifier.height(25.dp),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        )

        // Email Input
        OutlinedTextField(
            value = tanggal, onValueChange = {
                tanggal = it
            },
            label = {
                Text(
                    text = stringResource(id = R.string.input_date_label),
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
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.time_label),
            modifier = Modifier.height(25.dp),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        )
        // Password Input
        OutlinedTextField(value = waktu, onValueChange = {
            waktu = it
        }, label = {
            Text(
                text = stringResource(id = R.string.waktu_label),
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
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.lokasi_label),
            modifier = Modifier.height(25.dp),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        )

        // Password Input
        OutlinedTextField(value = lokasi, onValueChange = {
            lokasi = it
        }, label = {
            Text(
                text = stringResource(id = R.string.input_lokasi_label),
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
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.news_description_label),
            modifier = Modifier.height(25.dp),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        )

        OutlinedTextField(
            value = deskripsi,
            onValueChange = { deskripsi = it },
            label = {
                Text(
                    text = stringResource(id = R.string.input_news_description_label),
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors( // Warna garis batas saat difokuskan
                unfocusedBorderColor = Color.Gray,             // Warna garis batas saat tidak difokuskan
                focusedLabelColor = Color.Black,               // Warna label ketika difokuskan // Warna label ketika tidak difokuskan
                cursorColor = Color.Black                      // Warna kursor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

// Add Photo/Video
        Text(
            text = stringResource(id = R.string.add_photo_video_label),
            modifier = Modifier.height(25.dp),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f) // Membagi ukuran secara proporsional
                    .aspectRatio(1f) // Mengatur rasio 1:1 agar tinggi proporsional terhadap lebar
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .clickable { /* Add photo action */ },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.camera),
                        contentDescription = "Camera Icon",
                        modifier = Modifier.size(48.dp) // Ukuran ikon
                    )
                    Text(
                        text = stringResource(id = R.string.photo_label),
                        color = Color.Gray,
                        style = TextStyle(fontSize = 12.sp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .weight(1f) // Membagi ukuran secara proporsional
                    .aspectRatio(1f) // Mengatur rasio 1:1 agar tinggi proporsional terhadap lebar
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .clickable { /* Add video action */ },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.video),
                        contentDescription = "Video Icon",
                        modifier = Modifier.size(48.dp) // Ukuran ikon
                    )
                    Text(
                        text = stringResource(id = R.string.video_label),
                        color = Color.Gray,
                        style = TextStyle(fontSize = 12.sp)
                    )
                }
            }
        }

        Text(
            text = stringResource(id = R.string.personal_data_lable),
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = stringResource(id = R.string.sub_personal_data_label),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.phone_number_label),
            modifier = Modifier.height(25.dp),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        )

        // Password Input
        OutlinedTextField(value = lokasi, onValueChange = {
            lokasi = it
        }, label = {
            Text(
                text = stringResource(id = R.string.input_phone_number_label),
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
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.bank_account_label),
            modifier = Modifier.height(25.dp),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        )

        Spacer(modifier = Modifier.height(16.dp))
        // drop down menu bank
        ExposedDropdownMenuBox(
                expanded = isExpanded,
                modifier = Modifier
                    .fillMaxWidth(),
                onExpandedChange = {isExpanded = !isExpanded}
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .height(50.dp),
                    value = selectedBank, onValueChange = {},
                    label = {
                        Text(
                            text = stringResource(id = R.string.select_bank_account_label),
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
                    ),
                    readOnly = true,
                    trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)},

                )
                ExposedDropdownMenu( expanded = isExpanded, onDismissRequest = { isExpanded = false}) {
                    banks.forEachIndexed{index, text ->
                    DropdownMenuItem(
                        text = {Text(text=text)},
                        onClick = {selectedBank = banks[index]
                        isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                    }
                }
            }
        Spacer(modifier = Modifier.height(20.dp))
        // Input untuk nomor rekening
        OutlinedTextField(
            value = no_rek,
            onValueChange = { no_rek = it },
            label = {
                Text(
                    text = stringResource(id = R.string.bank_account_number_label),
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,               // Warna garis batas saat difokuskan
                unfocusedBorderColor = Color.Gray,             // Warna garis batas saat tidak difokuskan
                focusedLabelColor = Color.Black,               // Warna label ketika difokuskan
                unfocusedLabelColor = Color.Gray,              // Warna label ketika tidak difokuskan
                cursorColor = Color.Black                      // Warna kursor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = isConfirmed,
                onCheckedChange = { isConfirmed = it },
                colors = CheckboxDefaults.colors(colorResource(id = R.color.merah))
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "I confirm that all the information I have provided is accurate and can be held accountable.",
                style = TextStyle(fontSize = 12.sp),
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
// Create Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween // Mengatur jarak antar tombol
        ) {
            // Tombol Simpan
            Button(
                colors = ButtonDefaults.buttonColors(Color.White),
                onClick = {
                    // Aksi untuk tombol Simpan
                },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .weight(1f) // Membagi ruang secara proporsional
                    .height(50.dp)
                    .border(
                        BorderStroke(1.dp, colorResource(id = R.color.merah)),
                        shape = MaterialTheme.shapes.small
                    )
            ) {
                Text(
                    text = stringResource(id = R.string.simpan_label ),
                    color = colorResource(id = R.color.merah),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp)) // Jarak antar tombol

            // Tombol Send
            Button(
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.merah)),
                onClick = {
                    // Aksi untuk tombol Send
                },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .weight(1f) // Membagi ruang secara proporsional
                    .height(50.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.send_label),
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }

        }

    }


@Composable
fun TextFieldWithLabel(label: String, hint: String, isMultiline: Boolean = false) {
    val textState = remember { TextFieldValue("") }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold))
        BasicTextField(
            value = textState,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isMultiline) 120.dp else 56.dp)
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFormScreen() {
    FormPage()
}
