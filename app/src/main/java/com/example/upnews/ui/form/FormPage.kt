package com.example.app.view.upload

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.upnews.R
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.data.response.DataSave
import com.example.upnews.data.response.DataUpload
import com.example.upnews.ui.ViewModelFactory
import com.example.upnews.viewmodel.FormViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    uploadViewModel: FormViewModel = viewModel(
        factory = ViewModelFactory(UserPreferences.getInstance(LocalContext.current))),

    ) {

    val errorMessage by uploadViewModel.errorMessage.collectAsState()
    val successMessage by uploadViewModel.successMessage.collectAsState()
    val uploadResult by uploadViewModel.uploadResult.collectAsState()
    val isLoading by uploadViewModel.isLoading.collectAsState()


    // States untuk form fields
    var judul by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf("") }
    var waktu by remember { mutableStateOf("") }
    var lokasi by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var bukti by remember { mutableStateOf("") }
    var noHp by remember { mutableStateOf("") }
    var noRek by remember { mutableStateOf("") }
    val banks = listOf("Mandiri", "BNI", "BRI", "BSI", "BCA")
    var isExpanded by remember { mutableStateOf(false) }
    var selectedBank by remember { mutableStateOf("") }
    var isConfirmed by remember { mutableStateOf(false) }


    var showPopup by remember { mutableStateOf(false) }
    var popupMessage by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf(false) }
    val navigateToForm by uploadViewModel.navigateToForm.collectAsState()
    var fotoUri by remember { mutableStateOf<Uri?>(null) }
    var videoUri by remember { mutableStateOf<Uri?>(null) }

    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        fotoUri = uri
    }

    val selectVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        videoUri = uri
    }


    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            // Update tanggal dengan format yyyy-MM-dd
            val selectedDate = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            tanggal = dateFormat.format(selectedDate.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            // Update waktu dengan format hh:mm:ss
            val selectedTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0) // Set detik ke 0
            }
            val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            waktu = timeFormat.format(selectedTime.time)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true // true untuk format 24 jam
    )

    BackHandler { navController.popBackStack() }
    val scrollState = rememberScrollState()

    fun validateInputs(
        judul: String,
        tanggal: String,
        waktu: String,
        lokasi: String,
        deskripsi: String,
        noHp: String,
        noRek: String,
        isConfirmed: Boolean,
        selectedBank: String?,
        setErrorMessage: (String) -> Unit
    ): Boolean {
        return when {
            judul.isBlank() -> {
                setErrorMessage("Judul tidak boleh kosong")
                false
            }

            tanggal.isBlank() -> {
                setErrorMessage("Tanggal tidak boleh kosong")
                false
            }

            waktu.isBlank() -> {
                setErrorMessage("Waktu tidak boleh kosong")
                false
            }

            lokasi.isBlank() -> {
                setErrorMessage("Lokasi tidak boleh kosong")
                false
            }

            deskripsi.isBlank() -> {
                setErrorMessage("Deskripsi tidak boleh kosong")
                false
            }

            noHp.isBlank() -> {
                setErrorMessage("Nomor HP tidak boleh kosong")
                false
            }

            selectedBank.isNullOrBlank() -> {
                setErrorMessage("Pilih bank terlebih dahulu")
                false
            }

            noRek.isBlank() -> {
                setErrorMessage("Nomor rekening tidak boleh kosong")
                false
            }

            !isConfirmed -> {
                setErrorMessage("Anda harus menyetujui konfirmasi")
                false
            }

            else -> true
        }
    }

    // Menampilkan pesan error jika ada
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            // Menampilkan Toast dengan pesan error
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // Menambahkan scroll
            .padding(horizontal = 16.dp, vertical = 8.dp)

    ) {
        Text(
            text = stringResource(id = R.string.form_tittle),
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = stringResource(id = R.string.sub_tittle_label),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.tittle_label),
            modifier = Modifier.height(15.dp),
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
            isError = judul.isEmpty(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,               // Warna garis batas saat difokuskan
                unfocusedBorderColor = Color.Gray,             // Warna garis batas saat tidak difokuskan
                focusedLabelColor = Color.Black,               // Warna label ketika difokuskan
                unfocusedLabelColor = Color.Gray,              // Warna label ketika tidak difokuskan
                cursorColor = Color.Black                      // Warna kursor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.date_label),
            modifier = Modifier.height(15.dp),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        )

        OutlinedTextField(
            value = tanggal,
            onValueChange = {}, // Disable manual input, user hanya bisa memilih dari DatePicker
            readOnly = true, // Membuat field hanya bisa diisi dari DatePicker
            label = {
                Text(
                    text = stringResource(id = R.string.input_date_label),
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    // Tampilkan DatePickerDialog saat field diklik
                    datePickerDialog.show()
                }
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.time_label),
            modifier = Modifier.height(15.dp),
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        )
        OutlinedTextField(
            value = waktu,
            onValueChange = {}, // Disable manual input, hanya bisa dari TimePicker
            readOnly = true, // Membuat field hanya bisa diisi dari TimePicker
            label = {
                Text(
                    text = stringResource(id = R.string.waktu_label),
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    // Tampilkan TimePickerDialog saat field diklik
                    timePickerDialog.show()
                }
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.lokasi_label),
            modifier = Modifier.height(15.dp),
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
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.news_description_label),
            modifier = Modifier.height(15.dp),
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
                focusedBorderColor = Color.Gray,               // Warna garis batas saat difokuskan
                unfocusedBorderColor = Color.Gray,             // Warna garis batas saat tidak difokuskan
                focusedLabelColor = Color.Black,               // Warna label ketika difokuskan
                unfocusedLabelColor = Color.Gray,              // Warna label ketika tidak difokuskan
                cursorColor = Color.Black                     // Warna kursor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

// Add Photo/Video
        Text(
            text = stringResource(id = R.string.add_photo_video_label),
            modifier = Modifier.height(15.dp),
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
                    .clip(RoundedCornerShape(8.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (fotoUri != null) {
                    AndroidView(
                        modifier = Modifier
                            .fillMaxSize(),
//                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                        factory = { context ->
                            ImageView(context).apply {
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageURI(fotoUri)
                                clipToOutline = true
                            }
                        }
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(
                            onClick = { selectImageLauncher.launch("image/*") }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.camera), // Menggunakan drawable kamera
                                contentDescription = "Camera Icon",
                                tint = Color.Unspecified, // Hilangkan tint jika ingin warna asli drawable
                                modifier = Modifier.size(48.dp) // Ukuran ikon
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.photo_label),
                            color = Color.Gray,
                            style = TextStyle(fontSize = 12.sp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .weight(1f) // Membagi ukuran secara proporsional
                    .aspectRatio(1f) // Mengatur rasio 1:1 agar tinggi proporsional terhadap lebar
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (videoUri != null) {
                    // Menampilkan video jika sudah dipilih
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->
                            VideoView(context).apply {
                                this.setVideoURI(videoUri)
                                start()
                            }
                        }
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(
                            onClick = { selectVideoLauncher.launch("video/*") }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.video), // Menggunakan drawable video
                                contentDescription = "Video Icon",
                                tint = Color.Unspecified, // Hilangkan tint jika ingin warna asli drawable
                                modifier = Modifier.size(48.dp) // Ukuran ikon
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.video_label),
                            color = Color.Gray,
                            style = TextStyle(fontSize = 12.sp)
                        )
                    }
                }
            }

        }
            Text(
                text = stringResource(id = R.string.personal_data_lable),
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = stringResource(id = R.string.sub_personal_data_label),
                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.phone_number_label),
                modifier = Modifier.height(25.dp),
                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            )

            // phone Number Input
            OutlinedTextField(value = noHp, onValueChange = {
                noHp = it
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
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.bank_account_label),
                modifier = Modifier.height(15.dp),
                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            )

            Spacer(modifier = Modifier.height(10.dp))
            // drop down menu bank
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                onExpandedChange = { isExpanded = !isExpanded }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .height(60.dp),
                    value = selectedBank ?: "",
                    onValueChange = {},
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
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },

                    )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }) {
                    banks.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            text = { Text(text = text) },
                            onClick = {
                                selectedBank = banks[index] // Simpan bank yang dipilih
                                isExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // Input untuk nomor rekening
            OutlinedTextField(
                value = noRek,
                onValueChange = { noRek = it },
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
                    .height(60.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
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
            Spacer(modifier = Modifier.height(10.dp))
// Create Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween // Mengatur jarak antar tombol
            ) {
                LaunchedEffect(uploadResult, errorMessage) {
                    if (uploadResult != null) {
                        isSuccess = true
                        showPopup = true
                        Toast.makeText(
                            context,
                            "News have been successfully saved!",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Tunggu sebelum navigasi
                        delay(1000)
                        navController.navigate("upload") {
                            popUpTo("form") { inclusive = true }
                        }
                    } else if (!errorMessage.isNullOrEmpty()) {
                        popupMessage = errorMessage ?: "An error occurred"
                        isSuccess = false
                        showPopup = true
                    }
                }

                Button(
                    colors = ButtonDefaults.buttonColors(Color.White),
                    onClick = {
                        // Kumpulkan data dari form
                        val draftData = DataSave(
                            judul = judul,
                            tanggal = tanggal,
                            waktu = waktu,
                            lokasi = lokasi,
                            deskripsi = deskripsi,
                            bukti = bukti,
                            noHp = noHp,
                            noRek = noRek,
                            status = "draft" // Status ditetapkan sebagai "draft"
                        )

                        // Simpan data draft melalui ViewModel
                        uploadViewModel.saveDraftData(draftData)

                    },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .border(
                            BorderStroke(1.dp, colorResource(id = R.color.merah)),
                            shape = MaterialTheme.shapes.small
                        ),
                    enabled = !isLoading // Tombol dinonaktifkan saat loading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = colorResource(id = R.color.merah),
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.simpan_label),
                            color = colorResource(id = R.color.merah),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }

                    if (showPopup) {
                        AlertDialog(
                            onDismissRequest = {
                                showPopup = false
                                uploadViewModel.resetState()
                            },
                            title = {
                                Text(text = if (isSuccess) "Success" else "Failed")
                            },
                            text = {
                                Text(text = popupMessage)
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        showPopup = false
                                        uploadViewModel.resetState() // Reset status pada ViewModel

                                        if (isSuccess) {
                                            // Navigasi setelah berhasil
                                            navController.navigate("upload") {
                                                popUpTo("form") { inclusive = true }
                                            }
                                            // Delay sebelum reset state
                                            CoroutineScope(Dispatchers.Main).launch {
                                                delay(1000)
                                                uploadViewModel.resetState()
                                            }
                                        }

                                    },
                                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.merah)),
                                    shape = MaterialTheme.shapes.small,
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Text(
                                        text = "Okay",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        )
                    }


                }

                Spacer(modifier = Modifier.width(8.dp)) // Jarak antar tombol

// Tombol Send
                Button(
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.merah)),
                    onClick = {
                        if (validateInputs(
                                judul = judul,
                                tanggal = tanggal,
                                waktu = waktu,
                                lokasi = lokasi,
                                deskripsi = deskripsi,
                                noHp = noHp,
                                noRek = noRek,
                                isConfirmed = isConfirmed,
                                selectedBank = selectedBank,
                                setErrorMessage = { message ->
                                    uploadViewModel.setErrorMessage(message)
                                }
                            )
                        ) {
                            uploadViewModel.uploadNews(
                                DataUpload(
                                    judul = judul,
                                    tanggal = tanggal,
                                    waktu = waktu,
                                    lokasi = lokasi,
                                    deskripsi = deskripsi,
                                    noHp = noHp,
                                    noRek = noRek
                                ),
                                fotoUri,
                                context
                            )
                        }
                    },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    enabled = !isLoading
                ) {
                    Text(
                        text = stringResource(id = R.string.send_label),
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LaunchedEffect(navigateToForm) {
                    if (navigateToForm) {
                        // Navigasi ke UploadPage dan set selectedTabIndex ke 1 (Draft)
                        navController.navigate("upload") {
                            // Pop page sebelumnya (form) dari back stack agar tidak bisa kembali
                            popUpTo("form") { inclusive = true }
                        }
                        // Reset status navigasi setelah berhasil navigasi
                        uploadViewModel.resetState()
                    }
                }
            }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewFormScreen() {
//    FormPage()
//}
