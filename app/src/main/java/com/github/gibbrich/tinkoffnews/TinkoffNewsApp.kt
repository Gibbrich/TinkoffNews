package com.github.gibbrich.tinkoffnews

import android.app.Application
import android.arch.persistence.room.Room
import com.facebook.stetho.Stetho
import com.github.gibbrich.tinkoffnews.api.NewsAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Dvurechenskiyi on 07.03.2018.
 */
class TinkoffNewsApp : Application()
{
    lateinit var db: AppDatabase
    lateinit var newsAPI: NewsAPI

    override fun onCreate()
    {
        super.onCreate()

        instance = this
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "TinkoffNewsDb.db3").build()

        Stetho.initializeWithDefaults(this)

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.tinkoff.ru/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        newsAPI = retrofit.create(NewsAPI::class.java)
    }

    companion object
    {
        lateinit var instance: TinkoffNewsApp
            private set
    }
}