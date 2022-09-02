package com.jprudencio.shortly.data.remote.api.responses


import com.google.gson.annotations.SerializedName

data class ShortResponse(
    @SerializedName("ok")
    val ok: Boolean,
    @SerializedName("result")
    val result: ShortResult
)

data class ShortResult(
    @SerializedName("code")
    val code: String,
    @SerializedName("short_link")
    val shortLink: String,
    @SerializedName("full_short_link")
    val fullShortLink: String,
    @SerializedName("short_link2")
    val shortLink2: String,
    @SerializedName("full_short_link2")
    val fullShortLink2: String,
    @SerializedName("share_link")
    val shareLink: String,
    @SerializedName("full_share_link")
    val fullShareLink: String,
    @SerializedName("original_link")
    val originalLink: String
)