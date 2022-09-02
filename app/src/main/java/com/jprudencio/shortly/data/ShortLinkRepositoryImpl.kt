package com.jprudencio.shortly.data

import com.jprudencio.shortly.data.local.ShortLinkLocalDataSource
import com.jprudencio.shortly.data.local.room.ShortLinkLocal
import com.jprudencio.shortly.data.remote.ShortLinkRemoteDataSource
import com.jprudencio.shortly.model.ShortLink
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ShortLinkRepositoryImpl(
    private val shortLinkRemoteDataSource: ShortLinkRemoteDataSource,
    private val shortLinkLocalDataSource: ShortLinkLocalDataSource,
    private val defaultDispatcher: CoroutineDispatcher
) : ShortLinkRepository {

    override suspend fun short(url: String): Result<ShortLink> = withContext(defaultDispatcher) {
        shortLinkRemoteDataSource.shortLink(url).getOrElse {
            return@withContext Result.failure(it)
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
                Result.failure(Throwable())
        }

    override suspend fun getShortLinkHistory(): Flow<List<ShortLink>> =
        withContext(defaultDispatcher) {
            shortLinkLocalDataSource.getAllShortLinks()
                .map {
                    it.map { localLink ->
                        ShortLink(
                            localLink.id,
                            localLink.shortLink,
                            localLink.originalLink
                        )
                    }
                }
        }
}