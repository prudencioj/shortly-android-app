package com.jprudencio.shortly.ui.home.screens

import com.jprudencio.shortly.model.ShortLink

/**
 * Fake list of shortened links to be used in Compose Previews.
 */
val ShortLinksPreviewData = listOf(
    ShortLink(
        1L,
        "http://shortly.com/1",
        "http://averylongurl.com/1/letstrytomakteit/verybig/indeed"
    ),
    ShortLink(
        2L,
        "http://shortly.com/2",
        "http://averylongurl.com/2/letstrytomakteit/verybig/indeed"
    )
)