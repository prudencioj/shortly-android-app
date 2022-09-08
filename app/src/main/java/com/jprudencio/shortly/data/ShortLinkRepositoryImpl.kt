package com.jprudencio.shortly.data

import com.jprudencio.shortly.data.local.ShortLinkLocalDataSource
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

    /**
     * Gets a valid shorten link from the given url and returns a [Result].
     * In case of success the shorten link will be stored and accessible through the link history flow [getShortLinkHistory].
     */
    override suspend fun short(url: String): Result<ShortLink> = withContext(defaultDispatcher) {
        if (url.isEmpty()) {
            return@withContext Result.failure(Throwable("Invalid URl"))
        }

        // Attempt to get shorten link from the remote data source
        shortLinkRemoteDataSource.shortLink(url).getOrElse {
            return@withContext Result.failure(it)
        }.result.run {
            // Save the shortened link into the local data source and return the result
            val shortLink =
                shortLinkLocalDataSource.addShortLink(
                    ShortLink(
                        shortLink = shortLink,
                        originalLink = originalLink
                    )
                )
            Result.success(shortLink)
        }
    }

    /**
     * Deletes a short link from the history of previously shorten links.
     * In case of success the shorten link will no longer be accessed the updated history will be notified via [getShortLinkHistory].
     * [Result]
     */
    override suspend fun deleteShortLink(shortLink: ShortLink): Result<ShortLink> =
        withContext(defaultDispatcher) {
            val count = shortLinkLocalDataSource.deleteShortLink(shortLink)
            if (count >= 0) {
                Result.success(shortLink)
            } else {
                Result.failure(Throwable())
            }
        }

    /**
     * Returns a hot flow that keeps track of the existing history of successful shortened links.
     * Every time that a new entry is added/removed the flow will emit the list of the entire history.
     * Note if the same link is added multiple times it will be stored as different entries in the history.
     */
    override fun getShortLinkHistory(): Flow<List<ShortLink>> {
        return shortLinkLocalDataSource.getAllShortLinks()
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