package com.andzhaev.readerticket.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("id")
    @Expose
    val id: Long? = null,

    @SerializedName("textBooks")
    @Expose
    val textBooks: Long,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("author")
    @Expose
    val author: String,

    @SerializedName("genre")
    @Expose
    val genre: String,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("count")
    @Expose
    val count: Int
)