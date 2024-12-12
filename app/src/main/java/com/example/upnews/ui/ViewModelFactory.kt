package com.example.upnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.ui.draft.DraftViewModel
import com.example.upnews.ui.form.FormViewModel
import com.example.upnews.ui.homepage.HomeViewModel
import com.example.upnews.ui.login.LoginViewModel
import com.example.upnews.ui.signUp.SignUpViewModel

class ViewModelFactory(private val userPreferences: UserPreferences) : ViewModelProvider
.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(FormViewModel::class.java) -> {
                FormViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(DraftViewModel::class.java) -> {
                DraftViewModel(userPreferences) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}