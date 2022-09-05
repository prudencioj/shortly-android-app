package com.jprudencio.shortly.data

import com.jprudencio.shortly.data.remote.api.responses.ShortResponse
import com.jprudencio.shortly.data.remote.api.responses.ShortResult
import com.jprudencio.shortly.model.ShortLink

object ShortLinkTestData {
    val ShortLink = ShortLink(1L, "https://shortlink.com", "https://originallink.com")

    val ShortLinkList = listOf(
        ShortLink(1L, "https://shortlink1.com", "https://originallink1.com"),
        ShortLink(2L, "https://shortlink2.com", "https://originallink2.com")
    )

    val ShortLinkRemoteResponse = ShortResponse(
        true, ShortResult(
            "OK",
            "https://shortlink.com",
            "",
            "",
            "",
            "",
            "",
            "https://originallink.com"
        )
    )

    val ShortLinkSuccessResult =
        Result.success(ShortLink)
}