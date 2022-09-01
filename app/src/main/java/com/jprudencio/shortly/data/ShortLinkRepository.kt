package com.jprudencio.shortly.data

import com.jprudencio.shortly.data.room.ShortLinkLocal
import com.jprudencio.shortly.model.ShortLink
import kotlinx.coroutines.flow.Flow

interface ShortLinkRepository {
    suspend fun short(url: String): Result<ShortLink>

    suspend fun getShortLinkHistory(): Flow<List<ShortLinkLocal>>

    suspend fun deleteShortLink(shortLink: ShortLink): Result<Long>
}