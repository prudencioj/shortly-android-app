package com.jprudencio.shortly.ui.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jprudencio.shortly.R
import com.jprudencio.shortly.model.ShortLink
import com.jprudencio.shortly.ui.theme.ShortlyTheme

/**
 * Test Tag that identifies the list of shortened links in history.
 */
const val ShortLinkListTestTag = "ShortLinkListTestTag"

@Composable
fun ShortLinkList(
    modifier: Modifier = Modifier,
    shortLinks: List<ShortLink>,
    shortLinkClipboardCopied: ShortLink? = null,
    lazyListState: LazyListState = rememberLazyListState(),
    onDeleteButtonClick: (ShortLink) -> Unit,
    onShortLinkClipboardCopied: (ShortLink) -> Unit
) {
    Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_large)))
    LazyColumn(
        modifier = modifier.testTag(ShortLinkListTestTag),
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
                isClipboardCopied = shortLinkClipboardCopied?.id == item.id,
                onDeleteButtonClick = {
                    onDeleteButtonClick.invoke(it)
                },
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
    val buttonColor = if (isClipboardCopied) {
        MaterialTheme.colors.primaryVariant
    } else {
        MaterialTheme.colors.primary
    }

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
                color = MaterialTheme.colors.onSurface, thickness = 1.dp
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
                colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_medium),
                        end = dimensionResource(id = R.dimen.padding_medium),
                        top = dimensionResource(id = R.dimen.padding_small),
                        bottom = dimensionResource(id = R.dimen.padding_small)
                    ),
                onClick = {
                    clipboardManager.setText(AnnotatedString(shortLink.shortLink))
                    onShortLinkClipboardCopied.invoke(shortLink)
                },
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

// Preview

@Preview(device = Devices.PIXEL_4_XL)
@Composable
fun PreviewShortLinkList(modifier: Modifier = Modifier) {
    ShortlyTheme {
        ShortLinkList(
            modifier = modifier.background(MaterialTheme.colors.background),
            shortLinks =
            ShortLinksPreviewData,
            shortLinkClipboardCopied = null,
            onDeleteButtonClick = {},
            onShortLinkClipboardCopied = {}
        )
    }
}