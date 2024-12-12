package com.example.upnews.ui.signUp


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.data.response.RegisterResponse
import com.example.upnews.data.response.User
import com.example.upnews.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class SignUpViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val _registerResult = MutableLiveData<Result<RegisterResponse>>()
    val registerResult: LiveData<Result<RegisterResponse>> = _registerResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun register(nama: String, email: String, alamat: String, password: String) {
        // Validasi input pengguna
        if (nama.isBlank() || email.isBlank() || alamat.isBlank() || password.isBlank()) {
            _errorMessage.value = "Semua field harus diisi."
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Panggil API untuk registrasi
                val response = ApiConfig.apiService.register(nama, email, alamat, password)

                if (response.id != null && response.nama != null && response.email != null && response.alamat != null) {
                    // Simpan data registrasi ke UserPreferences menggunakan saveRegisterResponse
                    userPreferences.saveRegisterResponse(response)

                    // Kirim hasil sukses ke LiveData
                    _registerResult.value = Result.success(response)
                } else {
                    throw Exception("Response tidak lengkap. Data user tidak ditemukan.")
                }
            } catch (e: Exception) {
                // Tangani error dengan mengirimkan ke LiveData
                _registerResult.value = Result.failure(e)
                _errorMessage.value = e.message ?: "Terjadi kesalahan saat registrasi."
                Log.e("SignUpViewModel", "Error during registration: ${e.message}")
            } finally {
                // Hentikan indikator loading
                _isLoading.value = false
            }
        }
    }

    // Reset error message jika diperlukan
    fun resetErrorMessage() {
        _errorMessage.value = null
    }
}