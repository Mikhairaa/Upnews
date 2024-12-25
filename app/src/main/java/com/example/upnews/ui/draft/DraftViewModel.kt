package com.example.upnews.ui.draft

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.data.response.DataItem
import com.example.upnews.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DraftViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val _drafts = MutableStateFlow<List<DataItem>>(emptyList())
    val drafts: StateFlow<List<DataItem>> get() = _drafts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _deleteResult = MutableStateFlow<Result<Unit>?>(null)
    val deleteResult: StateFlow<Result<Unit>?> = _deleteResult

    // Fungsi untuk mengambil daftar draft berita
    fun fetchDrafts() {
        viewModelScope.launch {
            _isLoading.value = true

            // Mengambil token dari UserPreferences
            userPreferences.getToken().collect { token ->

                if (token.isNullOrEmpty()) {
                    _errorMessage.value = "No valid token found"
                    _isLoading.value = false
                    return@collect
                }

                try {
                    // Menggunakan token untuk mengambil data draft
                    val response = ApiConfig.apiService.getDraftBerita("Bearer $token")
                    Log.d("DraftViewModel", "API Response: ${response.message}")
                    if (response.data != null) {
                        _drafts.value = response.data.filterNotNull()
                    } else {
                        _errorMessage.value = "Failed to fetch drafts: ${response.message ?: "Unknown error"}"
                    }
                } catch (e: HttpException) {
                    _errorMessage.value = "HTTP Error: ${e.code()} - ${e.message()}"
                    Log.e("DraftViewModel", "HTTP Error: ${e.response()?.errorBody()?.string()}", e)
                } catch (e: Exception) {
                    _errorMessage.value = "Unexpected error: ${e.localizedMessage}"
                    Log.e("DraftViewModel", "Unexpected Error", e)
                }


                _isLoading.value = false
            }
        }
    }
    fun deleteDraft(idBerita: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _deleteResult.value = null

            userPreferences.getToken().collect { token ->
                if (token.isNullOrEmpty()) {
                    _errorMessage.value = "No valid token found"
                    _isLoading.value = false
                    return@collect
                }

                try {
                    val response = ApiConfig.apiService.deleteBerita("Bearer $token", idBerita.toString())
                    Log.d("Delete Draft", "Response: $response")
                    if (response.success == true) {
                        // Hapus draft dari daftar
                        _drafts.value = _drafts.value.filter { it.idBerita != idBerita }
                        _deleteResult.value = Result.success(Unit)
                        _errorMessage.value = "Draft successfully deleted!"
                    } else {
                        val error = response.message ?: "Unknown error occurred"
                        _deleteResult.value = Result.failure(Exception(error))
                        _errorMessage.value = "Failed to delete draft: $error"
                    }
                } catch (e: HttpException) {
                    val httpError = "HTTP Error: ${e.code()} - ${e.response()?.errorBody()?.string() ?: e.message()}"
                    _deleteResult.value = Result.failure(Exception(httpError))
                    _errorMessage.value = httpError
                } catch (e: Exception) {
                    val unexpectedError = "Unexpected error: ${e.localizedMessage}"
                    _deleteResult.value = Result.failure(Exception(unexpectedError))
                    _errorMessage.value = unexpectedError
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }
    fun resetState() {
        _deleteResult.value = null
        _errorMessage.value = null
    }
}

