package com.jprudencio.shortly.data

import com.jprudencio.shortly.model.ShortLink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeShortLinkRepository : ShortLinkRepository {
    private val data = listOf(
        ShortLink(
            1L,
            "http://shortly.com/1",
            "http://averylongurl.com/1/letstrytomakteit/verybig/indeed"
        )
    )
    private val dataFlow = MutableStateFlow(data)

    override suspend fun short(url: String): Result<ShortLink> {
        return Result.success(ShortLink(data.size.toLong(), url, url))
    }

    override suspend fun deleteShortLink(shortLink: ShortLink): Result<ShortLink> {
        return Result.success(shortLink)
    }

    override suspend fun getShortLinkHistory(): Flow<List<ShortLink>> {
        return dataFlow
    }
}