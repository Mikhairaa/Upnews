package com.example.upnews.viewmodel

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.data.response.DataSave
import com.example.upnews.data.response.UploadResponse
import com.example.upnews.data.response.DataUpload
import com.example.upnews.data.response.SaveDraftResponse
import com.example.upnews.data.retrofit.ApiConfig
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class FormViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val _uploadResult = MutableStateFlow<UploadResponse?>(null)
    val uploadResult: StateFlow<UploadResponse?> get() = _uploadResult

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _saveDraftResult = MutableStateFlow<SaveDraftResponse?>(null)
    val saveDraftResult: StateFlow<SaveDraftResponse?> get() = _saveDraftResult

    private val _navigateToForm = MutableStateFlow<Boolean>(false)  // Status navigasi
    val navigateToForm: MutableStateFlow<Boolean> get() = _navigateToForm
    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    // Fungsi untuk upload berita beserta file (foto/video)
    fun uploadNews(uploadData: DataUpload, fotoUri: Uri?, context: Context) {
        viewModelScope.launch {
            _isLoading.value = true

            userPreferences.getToken().collect { token ->
                if (token.isNullOrEmpty()) {
                    _errorMessage.value = "No valid token found"
                    _isLoading.value = false
                    return@collect
                }
                // Ambil userId dari UserPreferences
                userPreferences.getUserId().collect { userId ->
                    if (userId.isNullOrEmpty()) {
                        _errorMessage.value = "User ID not found"
                        _isLoading.value = false
                        return@collect
                    }

                    // Set idUser ke uploadData
                    uploadData.idUser = userId.toInt()


                    try {

                        Log.d("FormViewModel", "Token: $token")
                        Log.d("FormViewModel", "Upload Data: $uploadData")

                        val dataRequestBody = Gson().toJson(uploadData)
                            .toRequestBody("application/json".toMediaTypeOrNull())

                        // Mempersiapkan file yang akan diupload
                        val filePart: MultipartBody.Part? =
                            fotoUri?.let { prepareFilePart("bukti", it, context) }

                        if (filePart == null) {
                            _errorMessage.value = "File is required"
                            _isLoading.value = false
                            return@collect
                        }

                        // Mengirim data ke backend
                        val response = ApiConfig.apiService.upload(
                            token = "Bearer $token",
                            data = dataRequestBody,
                            bukti = filePart
                        )

                        Log.d("FormViewModel", "Response: ${response.message}")
                        if (response.success == true) {
                            _uploadResult.value = response
                            _successMessage.value = "News uploaded successfully!"
                        } else {
                            _errorMessage.value = "Upload failed: ${response.message}"
                        }
                    } catch (e: HttpException) {
                        Log.e("FormViewModel", "HTTP Error: ${e.code()} - ${e.message()}", e)
                        _errorMessage.value = "HTTP Error: ${e.code()}"
                    } catch (e: IOException) {
                        Log.e("FormViewModel", "Network Error: ${e.message}", e)
                        _errorMessage.value = "Network Error: ${e.message}"
                    } catch (e: Exception) {
                        Log.e("FormViewModel", "Unexpected Error: ${e.localizedMessage}", e)
                        _errorMessage.value = "Unexpected Error: ${e.localizedMessage}"
                    } finally {
                        _isLoading.value = false
                    }
                }
            }
        }
    }
        // Fungsi untuk mempersiapkan file part (bukti)
        private fun prepareFilePart(
            partName: String,
            fileUri: Uri,
            context: Context
        ): MultipartBody.Part? {
            val file = File(getRealPathFromURI(fileUri, context))
            val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(partName, file.name, requestBody)
        }

        // Fungsi untuk mendapatkan path asli dari URI file
        private fun getRealPathFromURI(contentUri: Uri, context: Context): String {
            val cursor = context.contentResolver.query(contentUri, null, null, null, null)
            cursor?.moveToFirst()
            val idx = cursor?.getColumnIndex(android.provider.MediaStore.Images.Media.DATA)
            val path = cursor?.getString(idx ?: -1)
            cursor?.close()
            return path ?: ""
        }

        fun resetState() {
            _saveDraftResult.value = null
            _errorMessage.value = null
            _navigateToForm.value = false
        }

        fun saveDraftData(draftData: DataSave) {
            viewModelScope.launch {
                _isLoading.value = true

                // Mengambil token dari SharedPreferences
                userPreferences.getToken().collect { token ->
                    if (token.isNullOrEmpty()) {
                        _errorMessage.value = "No valid token found"
                        _isLoading.value = false
                        return@collect
                    }

                    // Pengecekan jika field judul kosong
                    if (draftData.judul.isNullOrEmpty()) {
                        _errorMessage.value = "Failed to saved draft. Tittle is required"
                        _isLoading.value = false
                        return@collect
                    }

                    try {
                        val response = ApiConfig.apiService.saveDraft(
                            token = "Bearer $token",
                            draftData = draftData
                        )

                        Log.d("FormViewModel", "API SaveDraft Response: ${response.message}")

                        if (response.success == true) {
                            _saveDraftResult.value = response
                            _successMessage.value = "Draft saved successfully!"  // Pesan sukses
                            _errorMessage.value = null
                            _navigateToForm.value = true

                        } else {
                            _errorMessage.value = "Save draft failed: ${response.message}"
                        }
                    } catch (e: HttpException) {
                        _errorMessage.value = "HTTP Error: ${e.code()} - ${e.message()}"
                        Log.e(
                            "FormViewModel",
                            "HTTP Error: ${e.response()?.errorBody()?.string()}",
                            e
                        )
                    } catch (e: IOException) {
                        _errorMessage.value = "Network Error: ${e.message}"
                        Log.e("FormViewModel", "Network Error", e)
                    } catch (e: Exception) {
                        _errorMessage.value = "Unexpected Error: ${e.localizedMessage}"
                        Log.e("FormViewModel", "Unexpected Error", e)
                    }

                    _isLoading.value = false
                }
            }
        }

        fun setErrorMessage(message: String) {
            _errorMessage.value = message
        }
    }
