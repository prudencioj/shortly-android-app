package com.jprudencio.shortly.data.remote

import com.jprudencio.shortly.data.remote.api.responses.ShortResponse

/**
 * Responsible for getting a short URL from an original url using a remote Data Source.
 */
interface ShortLinkRemoteDataSource {
    suspend fun shortLink(url: String): Result<ShortResponse>
}