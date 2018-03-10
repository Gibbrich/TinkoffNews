package com.github.gibbrich.tinkoffnews.utils

import android.os.Build
import android.text.Html

/**
 * Created by Артур on 10.03.2018.
 */

fun String.fromHtml(): String
{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
    {
        Html.fromHtml(this , Html.FROM_HTML_MODE_LEGACY).toString()
    }
    else
    {
        Html.fromHtml(this).toString()
    }
}