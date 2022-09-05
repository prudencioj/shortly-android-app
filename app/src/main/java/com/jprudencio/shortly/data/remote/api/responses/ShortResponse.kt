package com.jprudencio.shortly.data.remote.api.responses


import com.google.gson.annotations.SerializedName

/**
 * Data class that represents a response from shrtco API.
 *
 * Not all of the fields returned from the API are represented here; only the ones used in this
 * project are listed below. For a full list of fields, consult the API documentation
 * [here](https://shrtco.de/docs).
 */
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
    @SerializedName("original_link")
    val originalLink: String
)