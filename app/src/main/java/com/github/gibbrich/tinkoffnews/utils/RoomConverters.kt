package com.github.gibbrich.tinkoffnews.utils

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by Артур on 09.03.2018.
 */
class RoomConverters
{
    @TypeConverter
    fun dateToTimestamp(date: Date) = date.time

    @TypeConverter
    fun timestampToDate(timestamp: Long) = Date(timestamp)
}