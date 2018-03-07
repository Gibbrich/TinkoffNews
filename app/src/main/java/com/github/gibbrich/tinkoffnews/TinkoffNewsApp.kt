package com.github.gibbrich.tinkoffnews

import android.app.Application
import android.arch.persistence.room.Room
import com.github.gibbrich.tinkoffnews.data.AppDatabase

/**
 * Created by Dvurechenskiyi on 07.03.2018.
 */
class TinkoffNewsApp : Application()
{
    private lateinit var db: AppDatabase

    override fun onCreate()
    {
        super.onCreate()

        instance = this
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "TinkoffNewsDb.db3").build()
    }

    companion object
    {
        lateinit var instance: TinkoffNewsApp
            private set
    }
}