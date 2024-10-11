package com.jprudencio.shortly.ui.home

import com.jprudencio.shortly.data.FakeShortLinkRepository
import com.jprudencio.shortly.data.ShortLinkTestData
import com.jprudencio.shortly.rules.MainDispatcherRule
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun getEmptyUiState() = runTest {
        val homeViewModel = HomeViewModel(FakeShortLinkRepository(emptyList()))

        val uiState = homeViewModel.uiState.value
        assertTrue(uiState is HomeUiState.NoHistory)
        assertFalse(uiState.isLoading)
    }

    @Test
    fun getInitialStateWithHistorySuccess() = runTest {
        val homeViewModel = HomeViewModel(FakeShortLinkRepository())

        val uiState = homeViewModel.uiState.value
        assertTrue(uiState is HomeUiState.HasHistory)
        assertFalse(uiState.isLoading)

        assertEquals(
            (uiState as HomeUiState.HasHistory).shortLinks,
            ShortLinkTestData.ShortLinkList
        )
    }

    @Test
    fun shortUrlUpdatesUiState() = runTest {
        val homeViewModel = HomeViewModel(FakeShortLinkRepository(emptyList()))

        homeViewModel.uiState.drop(1).first()
        assertTrue(homeViewModel.uiState.first() is HomeUiState.NoHistory)

        homeViewModel.shortUrl("http://testurl.com")

        assertTrue(homeViewModel.uiState.value is HomeUiState.HasHistory)
    }
}
