package com.jprudencio.shortly.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jprudencio.shortly.model.ShortLink

val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    lazyListState: LazyListState,
    onSelectLink: (String) -> Unit
) {
    Scaffold { contentPadding ->
        if (uiState is HomeUiState.HasHistory) {
            LazyColumn(
                contentPadding = WindowInsets.systemBars
                    .only(WindowInsetsSides.Bottom)
                    .add(WindowInsets(top = contentPadding.calculateTopPadding()))
                    .asPaddingValues(),
                state = lazyListState
            ) {
                itemsIndexed(items = uiState.shortLinks) { _, item ->
                    ShortLinkItem(shortLink = item, onSelect = onSelectLink)
                }
            }
        } else {
            Greeting(name = "empty screen")
        }
    }
}
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun ShortLinkItem(shortLink: ShortLink, onSelect: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 8.dp)
            .clickable {
                onSelect(shortLink.shortLink)
            }
    ) {

        Column(
            verticalArrangement = Arrangement.Top,
        ) {

            Column(
                modifier = Modifier.padding(8.dp, 12.dp)
            ) {
                Text(
                    text = shortLink.shortLink,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = shortLink.originalLink,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}
