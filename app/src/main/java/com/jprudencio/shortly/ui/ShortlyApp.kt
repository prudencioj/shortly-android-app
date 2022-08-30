package com.jprudencio.shortly.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jprudencio.shortly.ui.theme.ShortlyTheme

@Composable
fun ShortlyApp() {
    ShortlyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ShortlyNavGraph()
        }
    }
}