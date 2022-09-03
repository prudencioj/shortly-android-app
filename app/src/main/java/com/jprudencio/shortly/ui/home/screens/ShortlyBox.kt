package com.jprudencio.shortly.ui.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.jprudencio.shortly.R
import com.jprudencio.shortly.ui.theme.ShortlyTheme
import kotlinx.coroutines.delay


@Composable
fun ShortlyBox(
    modifier: Modifier = Modifier,
    onButtonClick: (String) -> Unit
) {
    val inputValue =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val displayHelper = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(5000)
        displayHelper.value = inputValue.value.text.isEmpty()
    }
    val inputBorderColor =
        if (displayHelper.value) MaterialTheme.colors.error else MaterialTheme.colors.surface
    val inputTextColor =
        if (displayHelper.value) MaterialTheme.colors.error else MaterialTheme.colors.onSurface
    val inputTextId =
        if (displayHelper.value) R.string.shortly_box_input_description else R.string.shortly_box_input_hint

    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.primaryVariant
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
                    .background(MaterialTheme.colors.primaryVariant),
                tint = Color.Unspecified
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_large))
        ) {
            OutlinedTextField(
                value = inputValue.value,
                onValueChange = {
                    inputValue.value = it
                    displayHelper.value = false
                },
                placeholder = {
                    Text(
                        stringResource(id = inputTextId),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.button,
                        color = inputTextColor,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = MaterialTheme.typography.h1.fontWeight,
                    fontFamily = MaterialTheme.typography.h1.fontFamily,
                    textAlign = TextAlign.Center,
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.round_corner_normal)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedBorderColor = inputBorderColor,
                    unfocusedBorderColor = inputBorderColor
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
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(
    device = Devices.PIXEL_4_XL,
    heightDp = 204
)
@Composable
fun PreviewShortlyBox(modifier: Modifier = Modifier) {
    ShortlyTheme {
        ShortlyBox(modifier) {}
    }
}