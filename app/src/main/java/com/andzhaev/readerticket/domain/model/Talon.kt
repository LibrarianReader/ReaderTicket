package com.andzhaev.readerticket.domain.model

data class Talon(
    val id: Int,
    val title: String,
    val genre: String,
    val number: Long,
    val date: String,
)