package com.jprudencio.shortly.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jprudencio.shortly.R
import com.jprudencio.shortly.ui.theme.DarkViolet
import com.jprudencio.shortly.ui.theme.LightGray
import com.jprudencio.shortly.ui.theme.ShortlyTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    lazyListState: LazyListState,
    onSelectLink: (String) -> Unit
) {
    Column {
        WelcomeTutorial(Modifier.weight(1.0f, true))
        ShortlyBox()
    }

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

/*
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
                    style = MaterialTheme.typography.h1
                )
                Text(
                    text = shortLink.originalLink,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}
*/

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
fun ShortlyBox(modifier: Modifier = Modifier) {
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
                onClick = {},
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

@Preview(
    device = Devices.PIXEL_4_XL
)
@Composable
fun PreviewHomeScreen(modifier: Modifier = Modifier) {
    ShortlyTheme {
        HomeScreen(
            modifier,
            uiState = HomeUiState.NoHistory(false, emptyList()),
            lazyListState = LazyListState(),
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

