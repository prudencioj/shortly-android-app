package com.jprudencio.shortly.data.remote.api

import com.jprudencio.shortly.data.remote.api.responses.ShortResponse

interface ShortLinkApiClient {
    suspend fun short(url: String): Result<ShortResponse>
}
