package com.jprudencio.shortly.data

import com.jprudencio.shortly.model.ShortLink

interface ShortLinkRepository {
    suspend fun short(url: String): Result<ShortLink>

    suspend fun getShortLinkHistory(): Result<List<ShortLink>>
}