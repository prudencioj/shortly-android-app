package com.jprudencio.shortly.api

import com.jprudencio.shortly.api.responses.ShortResponse

interface ShortLinkApiClient {
    suspend fun short(url: String): Result<ShortResponse>
}
