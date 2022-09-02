package com.jprudencio.shortly.data.local

import com.jprudencio.shortly.data.local.room.ShortLinkLocal
import com.jprudencio.shortly.data.local.room.ShortLinksDao
import com.jprudencio.shortly.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShortLinkLocalDataSource @Inject constructor(
    private val shortLinksDao: ShortLinksDao,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun addShortLink(shortLink: String, originalLink: String) =
        withContext(ioDispatcher) {
            val id = shortLinksDao.insert(
                ShortLinkLocal(
                    shortLink = shortLink,
                    originalLink = originalLink
                )
            )
            ShortLinkLocal(id, shortLink, originalLink)
        }

    suspend fun getAllShortLinks() = withContext(ioDispatcher) {
        shortLinksDao.getAllShortLinks()
    }

    suspend fun deleteShortLink(shortLink: ShortLinkLocal) = withContext(ioDispatcher) {
        shortLinksDao.delete(
            ShortLinkLocal(
                shortLink.id,
                shortLink.shortLink,
                shortLink.originalLink
            )
        )
    }
}