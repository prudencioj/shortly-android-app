package com.jprudencio.shortly.data.local

import com.jprudencio.shortly.data.local.room.ShortLinkLocal
import com.jprudencio.shortly.data.local.room.ShortLinksDao
import com.jprudencio.shortly.di.IODispatcher
import com.jprudencio.shortly.model.ShortLink
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShortLinkLocalDataSource @Inject constructor(
    private val shortLinksDao: ShortLinksDao,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun addShortLink(shortLink: ShortLink) =
        withContext(ioDispatcher) {
            val id = shortLinksDao.insert(shortLink.toShortLinkLocal())
            shortLink.copy(id = id)
        }

    suspend fun getAllShortLinks() = withContext(ioDispatcher) {
        shortLinksDao.getAllShortLinks()
    }

    suspend fun deleteShortLink(shortLink: ShortLink) = withContext(ioDispatcher) {
        shortLinksDao.delete(
            shortLink.toShortLinkLocal()
        )
    }
}

private fun ShortLink.toShortLinkLocal(): ShortLinkLocal {
    return ShortLinkLocal(this.id, this.shortLink, this.originalLink)
}
