package com.example.upnews.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.data.response.BeritaRejected
import com.example.upnews.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RejectedViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val _beritaRejected = MutableStateFlow<List<BeritaRejected?>?>(null)
    val beritaRejected: StateFlow<List<BeritaRejected?>?> get() = _beritaRejected

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun fetchRejectedBerita() {
        viewModelScope.launch {
            _isLoading.value = true
            userPreferences.getToken().collect { token ->
                if (token.isNullOrEmpty()) {
                    _errorMessage.value = "No valid token found"
                    _isLoading.value = false
                    return@collect
                }
                try {
                    val response = ApiConfig.apiService.getBeritaRejected("Bearer $token")
                    if (response.berita != null) {
                        _beritaRejected.value = response.berita.filterNotNull()
                    } else {
                        _errorMessage.value = "Failed to fetch rejected news: ${response.message ?: "Unknown error"}"
                    }
                } catch (e: HttpException) {
                    _errorMessage.value = "HTTP Error: ${e.code()} - ${e.message()}"
                } catch (e: Exception) {
                    _errorMessage.value = "Unexpected error: ${e.localizedMessage}"
                    Log.e("RejectedViewModel", "Unexpected Error", e)
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }
}
