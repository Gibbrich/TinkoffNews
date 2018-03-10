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
    override fun getNewsItem(id: Int): Flowable<News>
    {
        return TinkoffNewsApp
                .instance
                .newsAPI
                .getNewsData(id)
                .map {
                    val newsId = it.payload.title.id
                    val title = it.payload.title.text
                    val content = it.payload.content
                    val date = Date(it.payload.title.publicationDate.milliseconds)
                    News(newsId, title, content, date)
                }
    }

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