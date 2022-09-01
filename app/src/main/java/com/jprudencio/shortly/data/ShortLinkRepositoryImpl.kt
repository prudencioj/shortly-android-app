package com.jprudencio.shortly.data

import com.jprudencio.shortly.data.room.ShortLinkLocal
import com.jprudencio.shortly.model.ShortLink
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ShortLinkRepositoryImpl(
    private val shortLinkRemoteDataSource: ShortLinkRemoteDataSource,
    private val shortLinkLocalDataSource: ShortLinkLocalDataSource,
    private val defaultDispatcher: CoroutineDispatcher
) : ShortLinkRepository {

    override suspend fun short(url: String): Result<ShortLink> = withContext(defaultDispatcher) {
        shortLinkRemoteDataSource.shortLink(url).getOrElse {
            return@withContext Result.failure(Throwable("Unexpected error happened, please try again with a valid URL."))
        }.result.run {
            // save to local data source
            val shortLinkLocal =
                shortLinkLocalDataSource.addShortLink(shortLink, originalLink)

            Result.success(ShortLink(shortLinkLocal.id, shortLink, originalLink))
        }
    }

    override suspend fun deleteShortLink(shortLink: ShortLink): Result<Long> =
        withContext(defaultDispatcher) {
            val count = shortLinkLocalDataSource.deleteShortLink(
                ShortLinkLocal(
                    shortLink.id,
                    shortLink.shortLink,
                    shortLink.originalLink
                )
            )
            if (count >= 0) Result.success(count.toLong()) else
                Result.failure(Throwable("Failed to delete short link"))
        }

    override suspend fun getShortLinkHistory(): Flow<List<ShortLinkLocal>> =
        withContext(defaultDispatcher) {
            shortLinkLocalDataSource.getAllShortLinks()
        }
}