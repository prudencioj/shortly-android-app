package com.jprudencio.shortly.ui.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier

@Composable
fun HomeRoute(
    viewModel: HomeViewModel
) {
    val lazyListState: LazyListState = rememberLazyListState()

    viewModel.uiState.collectAsState().value.let { state ->
        HomeScreen(
            uiState = state,
            modifier = Modifier,
            lazyListState = lazyListState,
            onShortenLink = {
                viewModel.shortUrl(it)
            },
            onDeleteLink = {
                viewModel.deleteShortLink(it)
            },
            onLinkClipboardCopied = {
                viewModel.copyShortLink(it)
            }
        )
    }
}
