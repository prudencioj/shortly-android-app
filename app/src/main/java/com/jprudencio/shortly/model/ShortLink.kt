package com.jprudencio.shortly.model

/**
 * Data class for representing a shortened link.
 */
data class ShortLink(
    val id: Long = 0,
    val shortLink: String,
    val originalLink: String
)