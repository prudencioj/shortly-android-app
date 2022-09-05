package com.jprudencio.shortly.data

import com.jprudencio.shortly.model.ShortLink
import kotlinx.coroutines.flow.Flow

/**
 * Data repository that deals with shortening links and also keep track of all history of previously shortened links.
 */
interface ShortLinkRepository {

    suspend fun short(url: String): Result<ShortLink>

    suspend fun deleteShortLink(shortLink: ShortLink): Result<ShortLink>

    suspend fun getShortLinkHistory(): Flow<List<ShortLink>>
}