package com.example.upnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.upnews.data.local.UserPreferences
import com.example.upnews.ui.forgotPw.ChangePasswordViewModel
import com.example.upnews.ui.allnews.AllViewModel
import com.example.upnews.ui.draft.OnProgressViewModel
import com.example.upnews.ui.profile.ProfileViewModel
import com.example.upnews.ui.profile.UpdateProfilViewModel
import com.example.upnews.ui.screens.DoneViewModel
import com.example.upnews.ui.screens.RejectedViewModel
import com.example.upnews.ui.draft.DraftViewModel
import com.example.upnews.ui.draft.OnProgressViewModel
import com.example.upnews.ui.notifikasi.NotifikasiViewModel

class ViewModelFactory(private val userPreferences: UserPreferences) : ViewModelProvider
.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(UpdateProfilViewModel::class.java) -> {
                UpdateProfilViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(OnProgressViewModel::class.java) -> {
                OnProgressViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(RejectedViewModel::class.java) -> {
                RejectedViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(DoneViewModel::class.java) -> {
                DoneViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(AllViewModel::class.java) -> {
                AllViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> {
                ChangePasswordViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(NotifikasiViewModel::class.java) -> {
                NotifikasiViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(DraftViewModel::class.java) -> {
                DraftViewModel(userPreferences) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}