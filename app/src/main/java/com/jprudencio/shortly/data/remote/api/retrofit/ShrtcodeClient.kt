package com.jprudencio.shortly.data.remote.api.retrofit

import com.jprudencio.shortly.data.remote.api.ShortLinkApiClient
import com.jprudencio.shortly.data.remote.api.responses.ShortResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface ShrtcodeClient : ShortLinkApiClient {

    @POST("shorten/")
    override suspend fun short(@Query("url") url: String): Result<ShortResponse>
}
