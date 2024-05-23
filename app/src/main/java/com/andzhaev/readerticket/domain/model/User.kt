package com.andzhaev.readerticket.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    @Expose
    val id: Long? = null,

    @SerializedName("id_user")
    @Expose
    val id_user: Long,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("surname")
    @Expose
    val surname: String,

    @SerializedName("age")
    @Expose
    val age: Int = 0,

    @SerializedName("email")
    @Expose
    val email: String,

    @SerializedName("phone")
    @Expose
    val phone: String,

    @SerializedName("password")
    @Expose
    val password: String
)