package com.github.gibbrich.tinkoffnews.data

import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by Артур on 09.03.2018.
 */
interface INewsSource
{
    fun getNewsItem(id: Int): Flowable<News>
    fun getNews(): Flowable<List<News>>
    fun saveNews(news: List<News>)
    fun refreshNews()
}