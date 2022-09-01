package com.jprudencio.shortly.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jprudencio.shortly.R
import com.jprudencio.shortly.model.ShortLink
import com.jprudencio.shortly.ui.theme.Cyan
import com.jprudencio.shortly.ui.theme.DarkViolet
import com.jprudencio.shortly.ui.theme.LightGray
import com.jprudencio.shortly.ui.theme.ShortlyTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    lazyListState: LazyListState,
    onShortenLink: (String) -> Unit,
    onDeleteLink: (ShortLink) -> Unit,
    onLinkClipboardCopied: (ShortLink) -> Unit
) {
    Scaffold(modifier = modifier) { contentPadding ->
        Column {
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

            if (uiState.errorMessages.isNotEmpty()) {
                Toast.makeText(
                    LocalContext.current,
                    uiState.errorMessages.last(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
fun ShortLinkList(
    modifier: Modifier = Modifier,
    shortLinks: List<ShortLink>,
    shortLinkClipboardCopied: ShortLink? = null,
    lazyListState: LazyListState,
    onDeleteButtonClick: (ShortLink) -> Unit,
    onShortLinkClipboardCopied: (ShortLink) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_medium))
    ) {
        item {
            Text(
                text = stringResource(R.string.home_link_history_title),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_small))
            )
        }

        itemsIndexed(items = shortLinks,
            key = { _, item -> item.id }
        ) { _, item ->
            ShortLinkItem(
                shortLink = item,
                onDeleteButtonClick = {
                    onDeleteButtonClick.invoke(it)
                },
                isClipboardCopied = shortLinkClipboardCopied?.id == item.id,
                onShortLinkClipboardCopied = {
                    onShortLinkClipboardCopied.invoke(it)
                }
            )
        }
    }
}

@Composable
fun ShortLinkItem(
    modifier: Modifier = Modifier,
    shortLink: ShortLink,
    isClipboardCopied: Boolean,
    onDeleteButtonClick: (ShortLink) -> Unit,
    onShortLinkClipboardCopied: (ShortLink) -> Unit
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val buttonLabel = if (isClipboardCopied) {
        stringResource(R.string.home_link_history_item_copied)
    } else {
        stringResource(R.string.home_link_history_item_copy)
    }
    val buttonColor = if (isClipboardCopied) DarkViolet else Cyan

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column {
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_small)))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium),
                    top = dimensionResource(id = R.dimen.padding_small),
                    bottom = dimensionResource(id = R.dimen.padding_small)
                )
            ) {
                Text(
                    text = shortLink.originalLink,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f, fill = true)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_del),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.clickable(onClick = {
                        onDeleteButtonClick.invoke(shortLink)
                    })
                )
            }
            Divider(
                color = LightGray, thickness = 1.dp
            )
            Text(
                text = shortLink.shortLink,
                style = MaterialTheme.typography.h2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium),
                    top = dimensionResource(id = R.dimen.padding_small),
                    bottom = dimensionResource(id = R.dimen.padding_small)
                )
            )
            Button(
                onClick = {
                    clipboardManager.setText(AnnotatedString(shortLink.shortLink))
                    onShortLinkClipboardCopied.invoke(shortLink)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_medium),
                        end = dimensionResource(id = R.dimen.padding_medium),
                        top = dimensionResource(id = R.dimen.padding_small),
                        bottom = dimensionResource(id = R.dimen.padding_small)
                    )
            ) {
                Text(
                    text = buttonLabel,
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .alignByBaseline()
                        .wrapContentHeight()
                )
            }
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_small)))
        }
    }
}

@Composable
fun WelcomeTutorial(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_extra_large)))
            Icon(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_medium)))
            Icon(
                painter = painterResource(id = R.drawable.ic_illustration),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_medium)))
            Text(
                text = stringResource(R.string.home_title_get_started),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    ),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_small)))
            Text(
                text = stringResource(R.string.home_description_paste_your_link),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    ),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Composable
fun ShortlyBox(
    modifier: Modifier = Modifier,
    onButtonClick: (String) -> Unit
) {
    val inputValue = remember { mutableStateOf(TextFieldValue()) }

    Surface(
        modifier = modifier,
        color = DarkViolet
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painterResource(id = R.drawable.ic_shape),
                contentDescription = null,
                modifier = Modifier
                    .background(DarkViolet),
                tint = Color.Unspecified
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(48.dp)
        ) {
            TextField(
                value = inputValue.value,
                onValueChange = { inputValue.value = it },
                placeholder = {
                    Text(
                        stringResource(id = R.string.shortly_box_input_hint),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.caption,
                        color = LightGray,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = MaterialTheme.typography.h1.fontWeight,
                    fontFamily = MaterialTheme.typography.h1.fontFamily,
                    textAlign = TextAlign.Center
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.background
                )
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_small)))
            Button(
                onClick = {
                    onButtonClick.invoke(inputValue.value.text)
                    inputValue.value = TextFieldValue("")
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.shortly_box_short_button),
                    style = MaterialTheme.typography.h1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .alignByBaseline()
                        .wrapContentHeight()
                )
            }
        }
    }
}

// Preview

@Preview(
    device = Devices.PIXEL_4_XL
)
@Composable
fun PreviewHistoryHomeScreen(modifier: Modifier = Modifier) {
    val lazyListState: LazyListState = rememberLazyListState()

    ShortlyTheme {
        HomeScreen(
            modifier = modifier,
            uiState = HomeUiState.HasHistory(ShortLinksPreviewData, null, false, emptyList()),
            lazyListState = lazyListState,
            onShortenLink = {},
            onDeleteLink = {},
            onLinkClipboardCopied = {}
        )
    }
}

/*
@Preview(
    device = Devices.PIXEL_4_XL
)
@Composable
fun PreviewEmptyHomeScreen(modifier: Modifier = Modifier) {
    val lazyListState: LazyListState = rememberLazyListState()

    ShortlyTheme {
        HomeScreen(
            modifier = modifier,
            uiState = HomeUiState.NoHistory(false, emptyList()),
            lazyListState = lazyListState,
            onSelectLink = {}
        )
    }
}

@Preview(
    device = Devices.PIXEL_4_XL,
    heightDp = 204
)
@Composable
fun PreviewShortlyBox(modifier: Modifier = Modifier) {
    ShortlyTheme {
        ShortlyBox(modifier)
    }
}

@Preview(device = Devices.PIXEL_4_XL)
@Composable
fun PreviewWelcomeTutorial(modifier: Modifier = Modifier) {
    ShortlyTheme {
        WelcomeTutorial(modifier)
    }
}

@Preview(device = Devices.PIXEL_4_XL)
@Composable
fun PreviewShortLinkList(modifier: Modifier = Modifier) {
    val lazyListState: LazyListState = rememberLazyListState()

    ShortlyTheme {
        ShortLinkList(
            modifier = modifier,
            shortLinks =
            shortLinksPreviewData,
            lazyListState = lazyListState
        )
    }
}
 */