package com.example.upnews.ui.forgotPw

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.data.retrofit.ApiConfig.apiService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ChangePasswordViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    // Hasil status perubahan password
    private val _changePasswordResult = MutableLiveData<String>()
    val changePasswordResult: LiveData<String> get() = _changePasswordResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // Fungsi untuk mengganti password
    fun changePassword(passwordLama: String, passwordBaru: String, konfirmasiPassword: String) {
        viewModelScope.launch {
            _isLoading.value = true

            // Validasi input password
            if (passwordLama.isBlank() || passwordBaru.isBlank() || konfirmasiPassword.isBlank()) {
                _changePasswordResult.value = "Semua kolom harus diisi"
                _isLoading.value = false
                return@launch
            }

            if (passwordBaru != konfirmasiPassword) {
                _changePasswordResult.value = "Password baru dan konfirmasi password tidak cocok"
                _isLoading.value = false
                return@launch
            }

            // Ambil token dari UserPreferences
            userPreferences.getToken().collect { token ->
                if (token.isNullOrEmpty()) {
                    _changePasswordResult.value = "Token tidak ditemukan"
                    _isLoading.value = false
                    return@collect
                }

                // Log token untuk debugging
                Log.d("ChangePasswordViewModel", "Token ditemukan: $token")
                try {
                    // Panggil API untuk mengubah password
                    val response = apiService.changePassword(
                        "Bearer $token",
                        passwordLama = passwordLama,
                        passwordBaru = passwordBaru,
                        konfirmasiPassword = konfirmasiPassword
                    )

                    // Cek jika response berhasil
                    if (response != null) {
                        // Menyimpan password baru ke UserPreferences setelah berhasil
                        userPreferences.savePassword(passwordBaru)
                        _changePasswordResult.value = "Password berhasil diubah"
                    } else {
                        // Tangani jika response kosong atau null
                        _changePasswordResult.value = "Response kosong, gagal mengganti password"
                    }

                } catch (e: HttpException) {
                    // Tangani jika terjadi kesalahan HTTP (misalnya status code 4xx atau 5xx)
                    Log.e("ChangePasswordViewModel", "HTTP error: ${e.message()}")
                    _changePasswordResult.value = "Terjadi kesalahan pada server: ${e.message()}"
                } catch (e: IOException) {
                    // Tangani jika terjadi masalah dengan koneksi jaringan
                    Log.e("ChangePasswordViewModel", "Network error: ${e.message}")
                    _changePasswordResult.value = "Masalah jaringan, coba lagi nanti."
                } catch (e: Exception) {
                    // Tangani kesalahan lain yang tidak terduga
                    Log.e("ChangePasswordViewModel", "Error: ${e.message}")
                    _changePasswordResult.value = "Terjadi kesalahan tak terduga: ${e.message ?: "Unknown error"}"
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }
}
