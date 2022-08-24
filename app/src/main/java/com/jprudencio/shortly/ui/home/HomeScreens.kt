package com.jprudencio.shortly.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jprudencio.shortly.R
import com.jprudencio.shortly.model.ShortLink
import com.jprudencio.shortly.ui.theme.OffWhite
import com.jprudencio.shortly.ui.theme.White

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
    Homepage()

    /*
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
    }*/
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

@Preview(device = Devices.PIXEL_4_XL)
@Composable
fun Homepage(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = OffWhite
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.padding(top = 64.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_illustration),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            Text(
                text = "Let's get started!",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 48.dp, end = 48.dp),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp
            )
            Text(
                text = "Paste your first link into the field to shorten it",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 48.dp, end = 48.dp),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 17.sp
            )
        }
    }
}