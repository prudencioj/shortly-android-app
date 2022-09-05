package com.jprudencio.shortly.data.remote

import com.jprudencio.shortly.data.ShortLinkTestData.ShortLinkRemoteResponse
import com.jprudencio.shortly.data.remote.api.responses.ShortResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeShortLinkRemoteDataSource @OptIn(ExperimentalCoroutinesApi::class)
@Inject constructor(
    private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher(),
    private val shortResult: ShortResponse? = ShortLinkRemoteResponse
) : ShortLinkRemoteDataSource {
    override suspend fun shortLink(url: String) = withContext(dispatcher) {
        if (shortResult != null) Result.success(shortResult) else Result.failure(Throwable())
    }
}