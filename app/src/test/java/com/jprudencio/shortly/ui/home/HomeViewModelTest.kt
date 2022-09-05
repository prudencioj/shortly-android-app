package com.jprudencio.shortly.ui.home

import com.jprudencio.shortly.data.FakeShortLinkRepository
import com.jprudencio.shortly.data.ShortLinkTestData
import com.jprudencio.shortly.rules.MainDispatcherRule
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        assertTrue(uiState.errorMessages.isEmpty())
    }

    @Test
    fun getInitialStateWithHistorySuccess() = runTest {
        val homeViewModel = HomeViewModel(FakeShortLinkRepository())

        val uiState = homeViewModel.uiState.value
        assertTrue(uiState is HomeUiState.HasHistory)
        assertFalse(uiState.isLoading)
        assertTrue(uiState.errorMessages.isEmpty())

        assertEquals(
            (uiState as HomeUiState.HasHistory).shortLinks,
            ShortLinkTestData.ShortLinkList
        )
    }
}
