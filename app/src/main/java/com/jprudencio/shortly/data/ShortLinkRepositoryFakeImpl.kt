package com.jprudencio.shortly.data

import com.jprudencio.shortly.model.ShortLink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Fake repository which stores the history of shortened links in memory.
 */
class ShortLinkRepositoryFakeImpl : ShortLinkRepository {
    private val links = mutableListOf<ShortLink>()
    private val _shortLinkHistory = MutableSharedFlow<List<ShortLink>>(5)
    private var id = 0L

    override fun getShortLinkHistory(): Flow<List<ShortLink>> = _shortLinkHistory

    override suspend fun short(url: String): Result<ShortLink> {
        val shortLink = ShortLink(id = generateId(),
            shortLink = "http://short.ly/$id",
            originalLink = url)
        links.add(shortLink)
        _shortLinkHistory.emit(links.toList())
        return Result.success(shortLink)
    }

    override suspend fun deleteShortLink(shortLink: ShortLink): Result<ShortLink> {
        links.remove(shortLink)
        _shortLinkHistory.emit(links.toList())
        return Result.success(shortLink)
    }

    private fun generateId(): Long {
        id++
        return id
    }
}