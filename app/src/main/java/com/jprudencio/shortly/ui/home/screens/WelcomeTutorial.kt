package com.jprudencio.shortly.ui.home.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.jprudencio.shortly.R
import com.jprudencio.shortly.ui.theme.ShortlyTheme


@Composable
fun WelcomeTutorial(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
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
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
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
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

// Preview

@Preview(device = Devices.PIXEL_4_XL)
@Composable
fun PreviewWelcomeTutorial(modifier: Modifier = Modifier) {
    ShortlyTheme {
        WelcomeTutorial(modifier)
    }
}