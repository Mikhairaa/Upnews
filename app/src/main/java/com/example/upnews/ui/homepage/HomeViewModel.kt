package com.example.upnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upnews.data.response.BeritaHome
import com.example.upnews.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel : ViewModel() {

    // LiveData untuk menyimpan data user
    private val _userName = MutableLiveData<String?>()
    val userName: LiveData<String?> = _userName

    // LiveData untuk daftar berita
    private val _beritaList = MutableLiveData<List<BeritaHome>>()
    val beritaList: LiveData<List<BeritaHome>> = _beritaList

    // LiveData untuk status loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData untuk pesan error
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchHomepageData() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Memanggil API untuk mendapatkan data homepage
                val response = ApiConfig.apiService.getHomepageData()

                // Menyimpan data user ke LiveData
                _userName.value = response.user?.name

                // Menyimpan daftar berita ke LiveData
                _beritaList.value = response.berita?.filterNotNull() ?: emptyList()

                // Menghapus pesan error jika sebelumnya ada
                _errorMessage.value = null

            } catch (e: HttpException) {
                _errorMessage.value = "Server error: ${e.message()}"
            } catch (e: IOException) {
                _errorMessage.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
