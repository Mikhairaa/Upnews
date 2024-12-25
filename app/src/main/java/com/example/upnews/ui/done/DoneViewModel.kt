package com.example.upnews.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.data.response.BeritaDone  // Renamed from BeritaOnProgress
import com.example.upnews.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DoneViewModel(private val userPreferences: UserPreferences) : ViewModel() {  // Renamed from OnProgressViewModel

    private val _beritaDone = MutableStateFlow<List<BeritaDone?>?>(null)  // Renamed from _beritaOnProgress
    val beritaDone: StateFlow<List<BeritaDone?>?> get() = _beritaDone  // Renamed from beritaOnProgress

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun fetchDoneBerita() {  // Renamed from fetchOnProgressBerita
        viewModelScope.launch {
            _isLoading.value = true
            userPreferences.getToken().collect { token ->
                if (token.isNullOrEmpty()) {
                    _errorMessage.value = "No valid token found"
                    _isLoading.value = false
                    return@collect
                }
                try {
                    val response = ApiConfig.apiService.getBeritaDone("Bearer $token")  // Renamed from getBeritaOnProgress
                    if (response.berita != null) {
                        _beritaDone.value = response.berita.filterNotNull()  // Renamed from _beritaOnProgress
                    } else {
                        _errorMessage.value = "Failed to fetch drafts: ${response.message ?: "Unknown error"}"
                    }
                } catch (e: HttpException) {
                    _errorMessage.value = "HTTP Error: ${e.code()} - ${e.message()}"
                } catch (e: Exception) {
                    _errorMessage.value = "Unexpected error: ${e.localizedMessage}"
                    Log.e("DoneViewModel", "Unexpected Error", e)  // Renamed from OnProgressViewModel
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }
}
