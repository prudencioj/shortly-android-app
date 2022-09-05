package com.jprudencio.shortly.data

import com.jprudencio.shortly.data.ShortLinkTestData.ShortLinkList
import com.jprudencio.shortly.model.ShortLink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * A fake [ShortLinkRepository] to be used in tests. Persists the shortened links history in memory.
 */
class FakeShortLinkRepository(initialData: List<ShortLink> = ShortLinkList) :
    ShortLinkRepository {
    private val data = initialData.toMutableList()
    private val dataFlow = MutableStateFlow(data)

    override suspend fun short(url: String): Result<ShortLink> {
        val shortLink = ShortLink(dataFlow.value.size.toLong(), url, url)
        data.add(shortLink)
        dataFlow.emit(data)
        return Result.success(shortLink)
    }

    override suspend fun deleteShortLink(shortLink: ShortLink): Result<ShortLink> {
        return Result.success(shortLink)
    }

    override suspend fun getShortLinkHistory(): Flow<List<ShortLink>> {
        return dataFlow
    }
}