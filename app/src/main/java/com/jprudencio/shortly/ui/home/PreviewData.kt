package com.jprudencio.shortly.ui.home

import com.jprudencio.shortly.model.ShortLink

val ShortLinksPreviewData = generatePreviewData()

private fun generatePreviewData(entries: Int = 2): List<ShortLink> {
    val data = mutableListOf<ShortLink>()
    for (i in 1L until entries) {
        data.add(
            ShortLink(
                i,
                "http://shortly.com/$i",
                "http://averylongurl.com/$i/letstrytomakteit/verybig/indeed"
            )
        )
    }
    return data
}