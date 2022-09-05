package com.jprudencio.shortly.data.remote.api

import com.jprudencio.shortly.data.remote.api.responses.ShortResponse

/**
 * API client responsible for shortening an URL.
 */
interface ShortLinkApiClient {
    suspend fun short(url: String): Result<ShortResponse>
}