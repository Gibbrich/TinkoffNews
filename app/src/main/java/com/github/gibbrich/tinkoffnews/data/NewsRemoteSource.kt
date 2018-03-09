package com.github.gibbrich.tinkoffnews.data

import io.reactivex.Flowable

/**
 * Created by Артур on 09.03.2018.
 */
object NewsRemoteSource : INewsSource
{
    override fun saveNews(news: List<News>)
    {
        // do nothing
    }

    override fun getNews(): Flowable<List<News>>
    {
        // todo implement
        return Flowable.empty<List<News>>()
    }
}