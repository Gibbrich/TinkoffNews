package com.github.gibbrich.tinkoffnews.data

import com.github.gibbrich.tinkoffnews.TinkoffNewsApp
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Артур on 09.03.2018.
 */
object NewsLocalSource: INewsSource
{
    override fun deleteAllNews()
    {
        Completable.fromAction { TinkoffNewsApp.instance.db.newsDao.deleteAllNews() }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    override fun refreshNews()
    {
        // Not required
    }

    override fun getNewsItem(id: Int): Flowable<News>
    {
        return TinkoffNewsApp.instance.db.newsDao.getNewsItem(id).toFlowable()
    }

    override fun saveNews(news: List<News>)
    {
        Flowable.just(news)
                .subscribeOn(Schedulers.io())
                .subscribe { TinkoffNewsApp.instance.db.newsDao.insertAll(it) }
    }

    override fun getNews() = TinkoffNewsApp.instance.db.newsDao.getNewsHeaders()
}