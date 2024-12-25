package com.example.upnews.ui.homepage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.data.response.BeritaHome
import com.example.upnews.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(private val userPreferences: UserPreferences) : ViewModel() {


    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> get() = _userName


    private val _beritaList = MutableStateFlow<List<BeritaHome>>(emptyList())
    val beritaList: StateFlow<List<BeritaHome>> get() = _beritaList


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // StateFlow for error messages
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    // Function to fetch homepage data
    fun fetchHomepageData() {
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

                // Use the token to fetch homepage data
                val response = ApiConfig.apiService.getHomepageData("Bearer $token")
                Log.d("HomeViewModel", "API Response: ${response.message}")

                // Ensure userName is set only once
                response.user?.name?.let {
                    if (_userName.value == null) {
                        _userName.value = it
                    }
                }

                // Save the list of news articles to StateFlow, ensuring non-null entries
                _beritaList.value = response.berita?.filterNotNull() ?: emptyList()

                // Clear any previous error message
                _errorMessage.value = null

            } catch (e: HttpException) {
                // Handle server errors more clearly
                val errorBody = e.response()?.errorBody()?.string()
                _errorMessage.value = "Server error: ${e.code()} - ${errorBody ?: e.message()}"
                Log.e("HomeViewModel", "Server error", e)
            } catch (e: IOException) {
                // Handle network errors
                _errorMessage.value = "Network error: Please check your internet connection"
                Log.e("HomeViewModel", "Network error", e)
            } catch (e: Exception) {
                // Handle unexpected errors
                _errorMessage.value = "Unexpected error: ${e.localizedMessage}"
                Log.e("HomeViewModel", "Unexpected error", e)
            } finally {
                // Mark loading as false when operation is complete
                _isLoading.value = false
            }
        }
    }
}
