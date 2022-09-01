package com.jprudencio.shortly.model

data class ShortLink(
    val id: Long = 0,
    val shortLink: String,
    val originalLink: String
)
