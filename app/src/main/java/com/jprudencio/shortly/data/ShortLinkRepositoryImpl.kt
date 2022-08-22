package com.jprudencio.shortly.data

import com.jprudencio.shortly.model.ShortLink
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ShortLinkRepositoryImpl(
    private val shortLinkRemoteDataSource: ShortLinkRemoteDataSource,
    private val defaultDispatcher: CoroutineDispatcher
) : ShortLinkRepository {

    override suspend fun short(url: String): Result<ShortLink> = withContext(defaultDispatcher) {
        // TODO
        Result.success(
            shortLinkRemoteDataSource.shortLink(url).getOrElse { error ->
                return@withContext Result.failure(error)
            }.result.run {
                ShortLink("http://shortlink.com", "http://originallink.com")
            }
        )
    }

    override suspend fun getShortLinkHistory(): Result<List<ShortLink>> =
        withContext(defaultDispatcher) {
            // TODO
            Result.success(
                listOf(
                    ShortLink("http://shortlink1.com", "http://originallink1.com"),
                    ShortLink("http://shortlink2.com", "http://originallink2.com")
                )
            )
        }
}
