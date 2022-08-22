package com.jprudencio.shortly.ui.home

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

    }

    fun deleteUrl(url: String) {

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

                state.copy(shortLinks = feed, isLoading = false)
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
        override val isLoading: Boolean,
        override val errorMessages: List<String>
    ) : HomeUiState
}

private data class HomeViewModelState(
    val shortLinks: List<ShortLink>? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<String> = emptyList()
) {
    fun toUiState(): HomeUiState =
        when {
            shortLinks != null -> {
                HomeUiState.HasHistory(
                    shortLinks = shortLinks,
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
