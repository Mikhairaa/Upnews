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
            }catch (e: Exception) {
                _loginResult.postValue(Result.failure(e))
                Log.e("Loginerror", e.message ?: "Unknown error")
            }finally {
                _isLoading.postValue(false)
            }
        }
    }
}