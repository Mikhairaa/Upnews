package com.example.upnews.ui.allnews

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.data.response.BeritaAll
import com.example.upnews.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AllViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val _allNews = MutableStateFlow<List<BeritaAll>>(emptyList())
    val allNews: StateFlow<List<BeritaAll>> get() = _allNews

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> get() = _errorMessage

    fun fetchAllNews() {
        viewModelScope.launch {
            _isLoading.value = true

            userPreferences.getToken().collect { token ->
                if (token.isNullOrEmpty()) {
                    _errorMessage.value = "No valid token found"
                    _isLoading.value = false
                    return@collect
                }

                try {
                    val response = ApiConfig.apiService.getBeritaAll("Bearer $token")
                    Log.d("AllViewModel", "Response: $response")
                    if (!response.berita.isNullOrEmpty()) {
                        _allNews.value = response.berita.filterNotNull()
                    } else {
                        _errorMessage.value = "Failed to fetch drafts: ${response.message ?: "Unknown error"}"
                    }
                } catch (e: HttpException) {
                    _errorMessage.value = "HTTP Error: ${e.code()} - ${e.message()}"
                } catch (e: Exception) {
                    _errorMessage.value = "Unexpected error: ${e.localizedMessage}"
                    Log.e("AllViewModel", "Unexpected Error", e)
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }
}
