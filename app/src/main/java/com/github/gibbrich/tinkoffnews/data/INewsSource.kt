package com.github.gibbrich.tinkoffnews.data

import io.reactivex.Flowable

/**
 * Created by Артур on 09.03.2018.
 */
interface INewsSource
{
    fun getNews(): Flowable<List<News>>
    fun saveNews(news: List<News>)
}