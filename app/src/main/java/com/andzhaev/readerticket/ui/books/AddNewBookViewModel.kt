package com.andzhaev.readerticket.ui.books

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andzhaev.readerticket.data.network.ApiFactory
import com.andzhaev.readerticket.domain.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class AddNewBookViewModel : ViewModel() {
    private val _saveBookResult = MutableLiveData<Result<Unit>>()
    val saveBookResult: LiveData<Result<Unit>> get() = _saveBookResult

    fun saveBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) { // Запуск корутины на фоновом потоке
            try {
                Log.d("AddNewBookViewModel", "Sending book data: $book")
                val response = ApiFactory.apiService.saveBook(book).execute()
                if (response.isSuccessful) {
                    Log.d("AddNewBookViewModel", "Book saved successfully")
                    _saveBookResult.postValue(Result.success(Unit))
                } else {
                    val errorMessage = "Failed to save book: ${response.message()}"
                    Log.e("AddNewBookViewModel", errorMessage)
                    _saveBookResult.postValue(Result.failure(Exception(errorMessage)))
                }
            } catch (e: Exception) {
                Log.e("AddNewBookViewModel", "Exception occurred", e)
                _saveBookResult.postValue(Result.failure(e))
            }
        }
    }
}

