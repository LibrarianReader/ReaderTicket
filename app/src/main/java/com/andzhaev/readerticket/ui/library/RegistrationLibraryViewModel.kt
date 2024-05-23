package com.andzhaev.readerticket.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.andzhaev.readerticket.domain.model.User
import com.andzhaev.readerticket.domain.repository.UserRepository
import kotlinx.coroutines.launch

class RegistrationLibraryViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _registrationResult = MutableLiveData<Result<Unit>>()
    val registrationResult: LiveData<Result<Unit>> = _registrationResult

    fun registerUser(user: User) {
        viewModelScope.launch {
            try {
                val response = userRepository.saveUser(user)
                if (response.isSuccessful) {
                    _registrationResult.value = Result.success(Unit)
                } else {
                    _registrationResult.value = Result.failure(Throwable(response.message()))
                }
            } catch (e: Exception) {
                _registrationResult.value = Result.failure(e)
            }
        }
    }
}


