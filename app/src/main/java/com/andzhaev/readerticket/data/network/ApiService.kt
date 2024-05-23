package com.andzhaev.readerticket.data.network

import com.andzhaev.readerticket.domain.model.Book
import com.andzhaev.readerticket.domain.model.Talon
import com.andzhaev.readerticket.domain.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @GET("/book/get-all")
    fun getAllBooks(): Call<List<Book>>

    @POST("/book/save")
    fun saveBook(@Body book: Book): Call<Void>

    @POST("/user/save")
    suspend fun saveUser(@Body user: User): Response<Unit>

    @GET("/book/get-by-textbooks")
    suspend fun getBooksByTextBooks(@Query("textBooks") textBooks: Long): List<Book>

    @GET("/book/get-by-textbooks")
    suspend fun getBooksByGenre(@Query("genre") genre: String): List<Book>

    @GET("/book/talons")
    fun getTalons(): Call<List<Talon>>

    @POST("/book/talon/save")
    fun saveTalon(@Body talon: Talon): Call<Void>
}
