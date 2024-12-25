package com.example.upnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.ui.forgotPw.ChangePasswordViewModel
import com.example.upnews.ui.homepage.HomeViewModel
import com.example.upnews.ui.notifikasi.NotifikasiViewModel


class ViewModelFactory(
    private val userPreferences: UserPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> {
                ChangePasswordViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(NotifikasiViewModel::class.java) -> {
                NotifikasiViewModel(userPreferences) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
