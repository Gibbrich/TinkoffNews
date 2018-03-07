package com.github.gibbrich.tinkoffnews.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by Dvurechenskiyi on 07.03.2018.
 */
@Database(entities = arrayOf(News::class), version = 1)
abstract class AppDatabase: RoomDatabase()