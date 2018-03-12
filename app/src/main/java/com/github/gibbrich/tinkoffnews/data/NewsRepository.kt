package com.github.gibbrich.tinkoffnews.data

import android.support.annotation.VisibleForTesting
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by Артур on 09.03.2018.
 */
class NewsRepository private constructor(
        private val localSource: INewsSource = NewsLocalSource,
        private val remoteSource: INewsSource = NewsRemoteSource
) : INewsSource
{
    private val cachedNews: MutableMap<Int, News> = LinkedHashMap()

    @VisibleForTesting
    protected var isCacheDirty = false

    override fun getNews(): Flowable<List<News>>
    {
        if (!isCacheDirty)
        {
            if (cachedNews.isEmpty())
            {
                return localSource.getNews().firstElement().toFlowable()
            }
            else
            {
                return Flowable.just(cachedNews.values.toList())
            }
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

    override fun getNewsItem(id: Int): Flowable<News>
    {
        val news = cachedNews[id]
        if (news?.content != null)
        {
            return Flowable.just(news)
        }
        else
        {
            val newsItemFromLocalSource = getNewsItemFromLocalSource(id)
            val newsItemFromRemoteSource = getNewsItemFromRemoteSource(id)

            return Flowable.concat(newsItemFromLocalSource, newsItemFromRemoteSource)
                    .firstElement()
                    .toFlowable()
        }
    }

    private fun getNewsItemFromLocalSource(id: Int): Flowable<News>
    {
        return localSource.getNewsItem(id)
                .filter { it.content != null }
                .doOnNext { cachedNews.put(it.id, it) }
                .firstElement()
                .toFlowable()
    }

    private fun getNewsItemFromRemoteSource(id: Int): Flowable<News>
    {
        return remoteSource.getNewsItem(id)
                .doOnNext { saveNews(listOf(it)) }
    }

    override fun refreshNews()
    {
        isCacheDirty = true
    }

    override fun deleteAllNews()
    {
        cachedNews.clear()
        localSource.deleteAllNews()
        remoteSource.deleteAllNews()
    }

    companion object
    {
        private var INSTANCE: NewsRepository? = null

        fun getInstance(localSource: INewsSource, remoteSource: INewsSource): NewsRepository
        {
            return INSTANCE ?: NewsRepository(localSource, remoteSource).apply { INSTANCE = this }
        }

        fun destroyInstance()
        {
            INSTANCE = null
        }
    }
}