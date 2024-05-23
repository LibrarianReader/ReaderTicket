package com.andzhaev.readerticket.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andzhaev.readerticket.domain.repository.UserRepository

class RegistrationLibraryViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationLibraryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegistrationLibraryViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}