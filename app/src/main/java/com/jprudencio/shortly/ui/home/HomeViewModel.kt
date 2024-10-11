package com.jprudencio.shortly.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jprudencio.shortly.data.ShortLinkRepository
import com.jprudencio.shortly.model.ShortLink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A [ViewModel] for the Home screen. Responsible for getting the data from the data layer.
 * Emitting new [HomeUiState] every time there is a new state to be represented in the UI layer.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val shortLinkRepo: ShortLinkRepository) :
    ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true))

    // UI state exposed to the UI
    val uiState = combine(
        viewModelState,
        shortLinkRepo.getShortLinkHistory()
    ) { state, history ->
        state.copy(shortLinks = history, isLoading = false).toUiState()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, viewModelState.value.toUiState())

    fun shortUrl(url: String) {
        viewModelScope.launch {
            // TODO: Handle errors
            shortLinkRepo.short(url)
        }
    }

    fun deleteShortLink(shortLink: ShortLink) {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            // TODO: Handle errors
            shortLinkRepo.deleteShortLink(shortLink)
        }
    }

    fun copyShortLink(shortLink: ShortLink?) {
        viewModelState.update { state ->
            state.copy(copiedLink = shortLink)
        }
    }
}

sealed interface HomeUiState {
    val isLoading: Boolean

    data class NoHistory(
        override val isLoading: Boolean
    ) : HomeUiState

    data class HasHistory(
        val shortLinks: List<ShortLink>,
        val copiedLink: ShortLink? = null,
        override val isLoading: Boolean
    ) : HomeUiState
}

private data class HomeViewModelState(
    val shortLinks: List<ShortLink>? = null,
    val copiedLink: ShortLink? = null,
    val isLoading: Boolean = false
) {
    fun toUiState(): HomeUiState =
        when {
            !shortLinks.isNullOrEmpty() -> {
                HomeUiState.HasHistory(
                    shortLinks = shortLinks,
                    copiedLink = copiedLink,
                    isLoading = isLoading
                )
            }

            else -> {
                HomeUiState.NoHistory(
                    isLoading = isLoading
                )
            }
        }
}