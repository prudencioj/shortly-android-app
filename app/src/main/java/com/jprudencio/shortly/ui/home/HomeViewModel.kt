package com.jprudencio.shortly.ui.home

import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jprudencio.shortly.data.ShortLinkRepository
import com.jprudencio.shortly.model.ShortLink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


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
        refreshHistory()
    }

    fun shortUrl(url: String) {
        // FIXME temporary
        viewModelScope.launch {
            viewModelState.update { state ->
                var oldList = state.shortLinks ?: emptyList()
                oldList = oldList.toMutableList()
                oldList.add(ShortLink(url, "$url/shortly", url))
                state.copy(shortLinks = oldList, isLoading = false)
            }
        }
    }

    fun deleteShortLink(shortLink: ShortLink) {
        // FIXME temporary
        viewModelState.update { state ->
            var oldList = state.shortLinks ?: emptyList()
            oldList = oldList.toMutableList()
            oldList.remove(shortLink)

            if (oldList.isEmpty()) {
                state.copy(shortLinks = null, isLoading = false)
            } else {
                state.copy(shortLinks = oldList, isLoading = false)
            }
        }
    }

    fun copyShortLink(shortLink: ShortLink?) {
        viewModelState.update { state ->
            state.copy(copiedLink = shortLink)
        }
    }

    private fun refreshHistory() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = shortLinkRepo.getShortLinkHistory()
            viewModelState.update { state ->
                val feed = result.getOrElse { throwable ->
                    val errorMessages = state.errorMessages + (throwable.message ?: "")
                    return@update state.copy(errorMessages = errorMessages, isLoading = false)
                }
                if (feed.isEmpty()) {
                    state.copy(shortLinks = null, isLoading = false)
                } else {
                    state.copy(shortLinks = feed, isLoading = false)
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
            shortLinks != null -> {
                HomeUiState.HasHistory(
                    shortLinks = shortLinks,
                    copiedLink,
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