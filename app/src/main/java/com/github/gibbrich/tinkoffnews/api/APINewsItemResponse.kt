package com.github.gibbrich.tinkoffnews.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Артур on 09.03.2018.
 */

class APINewsItemResponse
{
    @SerializedName("resultCode")
    @Expose
    lateinit var resultCode: String

    @SerializedName("payload")
    @Expose
    lateinit var payload: APINewsItemData

    @SerializedName("trackingId")
    @Expose
    lateinit var trackingId: String
}

class APINewsItemData
{
    @SerializedName("title")
    @Expose
    lateinit var title: APINewsTitle

    @SerializedName("creationDate")
    @Expose
    lateinit var creationDate: APIDate

    @SerializedName("lastModificationDate")
    @Expose
    lateinit var lastModificationDate: APIDate

    @SerializedName("content")
    @Expose
    lateinit var content: String

    @SerializedName("bankInfoTypeId")
    @Expose
    var bankInfoTypeId: Int = 0

    @SerializedName("typeId")
    @Expose
    lateinit var typeId: String
}