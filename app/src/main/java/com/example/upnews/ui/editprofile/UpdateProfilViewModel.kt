package com.example.upnews.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.data.response.GetProfileResponse
import com.example.upnews.data.response.UpdateProfilResponse
import com.example.upnews.data.response.UserProfile
import com.example.upnews.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class UpdateProfilViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    // StateFlow untuk menyimpan data pengguna yang diambil dari API
    private val _userProfile = MutableStateFlow<GetProfileResponse?>(null)
    val userProfile: StateFlow<GetProfileResponse?> get() = _userProfile

    // StateFlow untuk status loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // StateFlow untuk pesan error
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun getUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true

            // Ambil token dari UserPreferences
            userPreferences.getToken().collect { token ->
                // Pastikan token valid
                if (token.isNullOrEmpty()) {
                    _errorMessage.value = "No valid token found"
                    _isLoading.value = false
                    return@collect
                }

                try {
                    // Menggunakan token untuk mengambil data profil
                    val response = ApiConfig.apiService.getUserProfile("Bearer $token")

                    // Cek apakah respons berhasil dan data user tersedia
                    if (response.message != null && response.user != null) {
                        _userProfile.value = response // Update StateFlow dengan data user
                    } else {
                        _errorMessage.value = "Failed to fetch profile"
                    }
                } catch (e: HttpException) {
                    _errorMessage.value = "HTTP Error: ${e.code()} - ${e.message()}"
                    Log.e(
                        "UpdateProfilViewModel",
                        "HTTP Error: ${e.response()?.errorBody()?.string()}",
                        e
                    )
                } catch (e: IOException) {
                    _errorMessage.value = "Network error: Please check your connection"
                    Log.e("UpdateProfilViewModel", "Network Error", e)
                } catch (e: Exception) {
                    _errorMessage.value = "Unexpected error: ${e.localizedMessage}"
                    Log.e("UpdateProfilViewModel", "Unexpected Error", e)
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }
    fun updateProfil(name: String, email: String, alamat: String, fotoProfil: String? = null) {
        viewModelScope.launch {
            Log.d("UpdateProfile", "Start updating profile with name: $name, email: $email, alamat: $alamat, fotoProfil: $fotoProfil")

            _isLoading.value = true

            try {
                // Ambil data user dari preferences
                val user = userPreferences.getUser().first()
                if (user == null) {
                    _errorMessage.value = "No user found in preferences"
                    Log.e("UpdateProfile", "Error: No user found in preferences")
                    _isLoading.value = false
                    return@launch
                }

                // Log user data untuk debugging
                Log.d("UpdateProfile", "User found: ${user.id}, ${user.nama}, ${user.email}, ${user.alamat}")

                // Validasi user ID
                val userId = user.id ?: run {
                    _errorMessage.value = "User ID is missing"
                    Log.e("UpdateProfile", "Error: User ID is missing")
                    _isLoading.value = false
                    return@launch
                }

                // Ambil token pengguna
                val token = userPreferences.getToken().first()
                if (token.isNullOrEmpty()) {
                    _errorMessage.value = "Token is missing"
                    Log.e("UpdateProfile", "Error: Token is missing")
                    _isLoading.value = false
                    return@launch
                }

                // Log token untuk debugging
                Log.d("UpdateProfile", "Token retrieved: $token")

                // Membuat body request
                val userProfile = UserProfile(
                    name = name,
                    email = email,
                    alamat = alamat,
                    fotoProfil = fotoProfil, // Optional
                    id = userId // ID tetap di body
                )

                // Log sebelum melakukan API request
                Log.d("UpdateProfile", "Sending update request with userId: $userId and token: $token")
                Log.d("UpdateProfile", "Request body: $userProfile")

                // Panggil API untuk update profil
                val response = ApiConfig.apiService.updateProfil(
                    "Bearer $token", // Token otorisasi
                    userId,          // ID pengguna
                    userProfile      // Body request
                )

                // Log response dari API
                Log.d("UpdateProfile", "Response from updateProfil: ${response.message}")

                if (response.message != null) {
                    _errorMessage.value = "Profile updated successfully"
                    Log.d("UpdateProfile", "Profile updated successfully: ${response.message}")
                } else {
                    _errorMessage.value = "Failed to update profile"
                    Log.e("UpdateProfile", "Failed to update profile: Response message is null")
                }

            } catch (e: Exception) {
                _errorMessage.value = "Error updating profile: ${e.localizedMessage}"
                Log.e("UpdateProfile", "Error during profile update: ${e.localizedMessage}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }


}