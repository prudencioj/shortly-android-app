package com.jprudencio.shortly.data

import com.jprudencio.shortly.model.ShortLink
import com.jprudencio.shortly.ui.home.shortLinksPreviewData
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
                ShortLink("1","http://shortlink.com", "http://originallink.com")
            }
        )
    }

    override suspend fun getShortLinkHistory(): Result<List<ShortLink>> =
        withContext(defaultDispatcher) {
            // TODO
            Result.success(
                shortLinksPreviewData
            )
        }
}
