package com.jprudencio.shortly.data

import com.jprudencio.shortly.data.local.ShortLinkLocalDataSource
import com.jprudencio.shortly.data.remote.FakeShortLinkRemoteDataSource
import com.jprudencio.shortly.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ShortLinkRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun shortUrlSuccess() = runTest {
        val mockShortLinkLocalDataSource = mockk<ShortLinkLocalDataSource>()
        coEvery { mockShortLinkLocalDataSource.addShortLink(any()) } answers { ShortLinkTestData.ShortLink }

        val shortLinkRepository =
            ShortLinkRepositoryImpl(
                FakeShortLinkRemoteDataSource(),
                mockShortLinkLocalDataSource,
                Dispatchers.Unconfined
            )

        assertEquals(
            ShortLinkTestData.ShortLinkSuccessResult,
            shortLinkRepository.short("https://originallink.com")
        )
    }

    @Test
    fun shortUrlInvalidUrl() = runTest {
        val shortLinkRepository =
            ShortLinkRepositoryImpl(
                FakeShortLinkRemoteDataSource(),
                mockk(),
                Dispatchers.Unconfined
            )

        assertTrue(
            shortLinkRepository.short("").isFailure
        )
    }

    @Test
    fun shortUrlReturnsApiError() = runTest {
        val shortLinkRepository =
            ShortLinkRepositoryImpl(
                FakeShortLinkRemoteDataSource(shortResult = null),
                mockk(),
                Dispatchers.Unconfined
            )

        assertTrue(
            shortLinkRepository.short("https://originalurl.com").isFailure
        )
    }
}
