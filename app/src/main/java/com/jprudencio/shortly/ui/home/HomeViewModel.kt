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
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        loadHistoryUpdates()
    }

    fun shortUrl(url: String) {
        viewModelScope.launch {
            val result = shortLinkRepo.short(url)

            viewModelState.update { state ->
                result.getOrElse { throwable ->
                    val errorMessages = state.errorMessages + (throwable.message ?: "")
                    return@update state.copy(errorMessages = errorMessages, isLoading = false)
                }
                state.copy(errorMessages = emptyList())
            }
        }
    }

    fun deleteShortLink(shortLink: ShortLink) {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = shortLinkRepo.deleteShortLink(shortLink)

            viewModelState.update { state ->
                result.getOrElse { throwable ->
                    val errorMessages = state.errorMessages + (throwable.message ?: "")
                    return@update state.copy(errorMessages = errorMessages, isLoading = false)
                }
                state.copy(errorMessages = emptyList())
            }
        }
    }

    fun copyShortLink(shortLink: ShortLink?) {
        viewModelState.update { state ->
            state.copy(copiedLink = shortLink)
        }
    }

    private fun loadHistoryUpdates() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val linkHistoryFlow = shortLinkRepo.getShortLinkHistory()
            linkHistoryFlow.collect { shortLinksHistory ->

                viewModelState.update { state ->
                    state.copy(shortLinks = shortLinksHistory, isLoading = false)
                }
            }
        }
    }
}

sealed interface HomeUiState {
    val isLoading: Boolean
    val errorMessages: List<String>

    data class NoHistory(
        override val isLoading: Boolean,
        override val errorMessages: List<String>
    ) : HomeUiState

    data class HasHistory(
        val shortLinks: List<ShortLink>,
        val copiedLink: ShortLink? = null,
        override val isLoading: Boolean,
        override val errorMessages: List<String>
    ) : HomeUiState
}

private data class HomeViewModelState(
    val shortLinks: List<ShortLink>? = null,
    val copiedLink: ShortLink? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<String> = emptyList()
) {
    fun toUiState(): HomeUiState =
        when {
            !shortLinks.isNullOrEmpty() -> {
                HomeUiState.HasHistory(
                    shortLinks = shortLinks,
                    copiedLink = copiedLink,
                    isLoading = isLoading,
                    errorMessages = errorMessages,
                )
            }
            else -> {
                HomeUiState.NoHistory(
                    isLoading = isLoading,
                    errorMessages = errorMessages,
                )
            }
        }
}