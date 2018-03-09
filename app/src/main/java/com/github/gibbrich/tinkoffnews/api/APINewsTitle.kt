package com.github.gibbrich.tinkoffnews.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Артур on 09.03.2018.
 */
class APINewsTitle
{
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("name")
    @Expose
    lateinit var name: String

    @SerializedName("text")
    @Expose
    lateinit var text: String

    @SerializedName("publicationDate")
    @Expose
    lateinit var publicationDate: APIDate

    @SerializedName("bankInfoTypeId")
    @Expose
    var bankInfoTypeId: Long = 0
}