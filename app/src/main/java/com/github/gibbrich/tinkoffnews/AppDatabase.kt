package com.github.gibbrich.tinkoffnews

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.github.gibbrich.tinkoffnews.data.News
import com.github.gibbrich.tinkoffnews.data.NewsDao
import com.github.gibbrich.tinkoffnews.utils.RoomConverters

/**
 * Created by Dvurechenskiyi on 07.03.2018.
 */
@Database(entities = arrayOf(News::class), version = 1)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase: RoomDatabase()
{
    abstract val newsDao: NewsDao
}