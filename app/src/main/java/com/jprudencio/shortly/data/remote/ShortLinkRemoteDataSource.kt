package com.jprudencio.shortly.data.remote

import com.jprudencio.shortly.data.remote.api.responses.ShortResponse

interface ShortLinkRemoteDataSource {
    suspend fun shortLink(url: String): Result<ShortResponse>
}