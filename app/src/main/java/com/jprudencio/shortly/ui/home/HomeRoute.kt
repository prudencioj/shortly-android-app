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
        when (state) {
            is HomeUiState.HasHistory -> HomeScreen(
                uiState = state,
                modifier = Modifier,
                lazyListState = lazyListState,
                onSelectLink = {
                    viewModel.deleteUrl("")
                }
            )
            is HomeUiState.NoHistory -> {

            }
        }
    }
}
