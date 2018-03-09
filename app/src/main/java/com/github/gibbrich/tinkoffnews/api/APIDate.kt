package com.github.gibbrich.tinkoffnews.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Артур on 09.03.2018.
 */

class APIDate
{
    @SerializedName("milliseconds")
    @Expose
    var milliseconds: Long = 0
}