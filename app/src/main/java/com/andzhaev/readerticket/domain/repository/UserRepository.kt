package com.andzhaev.readerticket.domain.repository

import com.andzhaev.readerticket.data.network.ApiService
import com.andzhaev.readerticket.domain.model.Book
import com.andzhaev.readerticket.domain.model.User
import retrofit2.Call
import retrofit2.Response

class UserRepository(val apiService: ApiService) {

    suspend fun saveUser(user: User): Response<Unit> {
        return apiService.saveUser(user)
    }

}