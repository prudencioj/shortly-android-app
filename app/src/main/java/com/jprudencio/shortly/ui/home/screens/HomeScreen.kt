package com.jprudencio.shortly.ui.home.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.jprudencio.shortly.R
import com.jprudencio.shortly.model.ShortLink
import com.jprudencio.shortly.ui.home.HomeUiState
import com.jprudencio.shortly.ui.theme.ShortlyTheme

/**
 * Home screen that will display UI components depending on the [HomeUiState] provided.
 * If no history is provided will display a [WelcomeTutorial] otherwise will show the history of shortened links in a [ShortLinkList].
 * [ShortlyBox] will always be visible being the action component to shorten new links.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    lazyListState: LazyListState = rememberLazyListState(),
    onShortenLink: (String) -> Unit,
    onDeleteLink: (ShortLink) -> Unit,
    onLinkClipboardCopied: (ShortLink) -> Unit
) {
    Column(
        modifier = modifier.padding(
            WindowInsets.systemBars
                .only(WindowInsetsSides.Bottom)
                .asPaddingValues()
        )
    ) {
        when (uiState) {
            is HomeUiState.NoHistory -> {
                WelcomeTutorial(modifier = Modifier.weight(1.0f, true))
            }
            is HomeUiState.HasHistory -> {
                ShortLinkList(
                    modifier = Modifier.weight(1.0f, true),
                    lazyListState = lazyListState,
                    shortLinks = uiState.shortLinks,
                    shortLinkClipboardCopied = uiState.copiedLink,
                    onDeleteButtonClick = { onDeleteLink.invoke(it) },
                    onShortLinkClipboardCopied = { onLinkClipboardCopied.invoke(it) }
                )
            }
        }
        ShortlyBox(onButtonClick = { onShortenLink.invoke(it) })
    }
}

// Preview

@Preview(
    device = Devices.PIXEL_4_XL
)
@Composable
fun PreviewEmptyHistoryHomeScreen(modifier: Modifier = Modifier) {
    ShortlyTheme {
        HomeScreen(
            modifier = modifier,
            uiState = HomeUiState.NoHistory(false),
            onShortenLink = {},
            onDeleteLink = {},
            onLinkClipboardCopied = {}
        )
    }
}

@Preview(
    device = Devices.PIXEL_4_XL
)
@Composable
fun PreviewHistoryHomeScreen(modifier: Modifier = Modifier) {
    ShortlyTheme {
        HomeScreen(
            modifier = modifier,
            uiState = HomeUiState.HasHistory(
                ShortLinksPreviewData,
                null, false
            ),
            onShortenLink = {},
            onDeleteLink = {},
            onLinkClipboardCopied = {}
        )
    }
}