package com.github.gibbrich.tinkoffnews

import android.app.Application
import android.arch.persistence.room.Room
import com.facebook.stetho.Stetho

/**
 * Created by Dvurechenskiyi on 07.03.2018.
 */
class TinkoffNewsApp : Application()
{
    lateinit var db: AppDatabase

    override fun onCreate()
    {
        super.onCreate()

        instance = this
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "TinkoffNewsDb.db3").build()

        Stetho.initializeWithDefaults(this)
    }

    companion object
    {
        lateinit var instance: TinkoffNewsApp
            private set
    }
}