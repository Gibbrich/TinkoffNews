package com.github.gibbrich.tinkoffnews.data

import com.github.gibbrich.tinkoffnews.TinkoffNewsApp
import com.github.gibbrich.tinkoffnews.api.APINewsListResponse
import io.reactivex.Flowable
import java.util.*

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
        return TinkoffNewsApp
                .instance
                .newsAPI
                .getNews()
                .flatMap { getNewsFromAPIResponse(it) }
    }

    private fun getNewsFromAPIResponse(response: APINewsListResponse): Flowable<List<News>>
    {
        return Flowable.fromIterable(response.payload)
                .map {
                    val publicationDate = Date(it.publicationDate.milliseconds)
                    News(it.id, it.text, null, publicationDate)
                }
                .toList()
                .toFlowable()
    }
}