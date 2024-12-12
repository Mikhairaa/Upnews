package com.example.upnews.ui.login


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.data.response.LoginResponse
import com.example.upnews.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class LoginViewModel(private val userPreferences: UserPreferences): ViewModel() {
    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginResult.postValue(Result.failure(Exception("Email dan password wajib diisi.")))
            return
        }
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.apiService.login(email, password)
                response.user?.let { user ->
                    user.token?.let { token ->
                        userPreferences.saveIsUserLoggedIn(true)
                        userPreferences.saveToken(token)
                        userPreferences.saveUser(user)
                        _loginResult.postValue(Result.success(response))
                    } ?: throw Exception("Token not found in responses")
                } ?: throw Exception("User not found in response")
            }catch (e: retrofit2.HttpException) {
                // Tangani kesalahan berdasarkan status kode HTTP
                when (e.code()) {
                    400 -> _loginResult.postValue(Result.failure(Exception("Email atau password salah.")))
                    401 -> _loginResult.postValue(Result.failure(Exception("Unauthorized: Email atau password salah.")))
                    else -> _loginResult.postValue(Result.failure(Exception("Kesalahan jaringan atau server: ${e.message}")))
                }
                Log.e("LoginHttpError", "HTTP ${e.code()}: ${e.message}")
            } catch (e: Exception) {
                // Tangani kesalahan umum lainnya
                _loginResult.postValue(Result.failure(Exception("Terjadi kesalahan: ${e.message}")))
                Log.e("LoginError", e.message ?: "Unknown error")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}