package com.example.upnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.ui.form.FormViewModel
import com.example.upnews.ui.login.LoginViewModel
import com.example.upnews.viewmodel.HomeViewModel

class ViewModelFactory(
    private val userPreferences: UserPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userPreferences) as T
            }
//            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
//                HomeViewModel(userPreferences) as T
//            }
            modelClass.isAssignableFrom(FormViewModel::class.java) -> {
                FormViewModel(userPreferences) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
