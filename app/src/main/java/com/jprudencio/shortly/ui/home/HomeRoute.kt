package com.jprudencio.shortly.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.jprudencio.shortly.ui.home.screens.HomeScreen

/**
 * Route responsible for launching the home screen and handling the navigation within that screen.
 * Connects the data from the [HomeViewModel] to the [HomeScreen] whenever a new data state occurs.
 */
@Composable
fun HomeRoute(
    viewModel: HomeViewModel
) {
    viewModel.uiState.collectAsState().value.let { state ->
        HomeScreen(
            uiState = state,
            modifier = Modifier,
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