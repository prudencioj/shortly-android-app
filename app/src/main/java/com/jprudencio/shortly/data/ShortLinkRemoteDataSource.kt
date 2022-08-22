package com.jprudencio.shortly.data

import com.jprudencio.shortly.api.ShortLinkApiClient
import com.jprudencio.shortly.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShortLinkRemoteDataSource @Inject constructor(
    private val shortLinkApiClient: ShortLinkApiClient,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun shortLink(url: String) = withContext(ioDispatcher) {
        shortLinkApiClient.short(url)
    }
}