package com.github.gibbrich.tinkoffnews.data

import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by Артур on 09.03.2018.
 */
object NewsRepository: INewsSource
{
    // todo switch to DI
    private val localSource: INewsSource = NewsLocalSource
    private val remoteSource: INewsSource = NewsRemoteSource

    private val cachedNews: MutableMap<Int, News> = HashMap()

    private var isFirstLoad = true // first load after app startup
    private var isCacheDirty = true

    override fun getNews(): Flowable<List<News>>
    {
        if (isFirstLoad)
        {
            isFirstLoad = false
            return localSource.getNews().firstElement().toFlowable()
        }

        if (!isCacheDirty)
        {
            return Flowable.just(cachedNews.values.toList())
        }

        cachedNews.clear()

        return remoteSource.getNews()
                .doOnNext { saveNews(it) }
                .doOnComplete { isCacheDirty = false }
    }

    override fun saveNews(news: List<News>)
    {
        news.forEach { cachedNews.put(it.id, it) }
        localSource.saveNews(news)
    }
}