package com.github.gibbrich.tinkoffnews.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Артур on 09.03.2018.
 */

class APINewsListResponse
{
    @SerializedName("resultCode")
    @Expose
    lateinit var resultCode: String

    @SerializedName("payload")
    @Expose
    lateinit var payload: List<APINewsTitle>

    @SerializedName("trackingId")
    @Expose
    lateinit var trackingId: String
}