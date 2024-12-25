package com.example.upnews.ui.notifikasi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upnews.data.response.DataNotifikasi

import com.example.upnews.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class NotifikasiViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val _notifikasiList = MutableStateFlow<List<DataNotifikasi>>(emptyList())
    val notifikasiList: StateFlow<List<DataNotifikasi>> get() = _notifikasiList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // StateFlow for error messages
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    // Function to fetch notifikasi data
    fun fetchNotifikasiData() {
        viewModelScope.launch {
            // Mark loading as true before starting data fetch
            _isLoading.value = true

            try {
                // Get token from UserPreferences
                val token = userPreferences.getToken().first()

                // Check if the token is valid
                if (token.isNullOrEmpty()) {
                    _errorMessage.value = "No valid token found"
                    return@launch
                }
// Pastikan response.data sudah terisi
                val response = ApiConfig.apiService.getNotifikasi("Bearer $token")
                Log.d("NotifikasiViewModel", "API Response: ${response.message}")

// Jika response.data tidak null, kita lakukan pemetaan
                if (response.dataNotifikasi != null) {
                    val notifikasiMapped = response.dataNotifikasi.filterNotNull().map { dataItem ->
                        // Map DataItem ke DataNotifikasi
                        DataNotifikasi(
                            createdAt = dataItem.createdAt,
                            idNotifikasi = dataItem.idNotifikasi,
                            idUser = dataItem.idUser,
                            deskripsi = dataItem.deskripsi,
                            idBerita = dataItem.idBerita,
                            status = dataItem.status,
                            updatedAt = dataItem.updatedAt
                        )
                    }
                    // Simpan hasil pemetaan ke StateFlow
                    _notifikasiList.value = notifikasiMapped
                } else {
                    // Tangani jika response.data null
                    _errorMessage.value = "Failed to fetch drafts: ${response.message ?: "Unknown error"}"
                }


            } catch (e: HttpException) {
                // Handle server errors more clearly
                val errorBody = e.response()?.errorBody()?.string()
                _errorMessage.value = "Server error: ${e.code()} - ${errorBody ?: e.message()}"
                Log.e("NotifikasiViewModel", "Server error", e)
            } catch (e: IOException) {
                // Handle network errors
                _errorMessage.value = "Network error: Please check your internet connection"
                Log.e("NotifikasiViewModel", "Network error", e)
            } catch (e: Exception) {
                // Handle unexpected errors
                _errorMessage.value = "Unexpected error: ${e.localizedMessage}"
                Log.e("NotifikasiViewModel", "Unexpected error", e)
            } finally {
                // Mark loading as false when operation is complete
                _isLoading.value = false
            }
        }
    }
}
