package com.github.gibbrich.tinkoffnews.data

import android.os.Build
import android.text.Html
import com.github.gibbrich.tinkoffnews.TinkoffNewsApp
import com.github.gibbrich.tinkoffnews.api.APINewsListResponse
import com.github.gibbrich.tinkoffnews.api.APIResultCodeValues
import com.github.gibbrich.tinkoffnews.utils.fromHtml
import io.reactivex.Flowable
import java.util.*

/**
 * Created by Артур on 09.03.2018.
 */
object NewsRemoteSource : INewsSource
{
    override fun deleteAllNews()
    {
        // Not required
    }

    override fun refreshNews()
    {
        // Not required
    }

    override fun getNewsItem(id: Int): Flowable<News>
    {
        return TinkoffNewsApp
                .instance
                .newsAPI
                .getNewsData(id)
                .flatMap {
                    if (it.resultCode == APIResultCodeValues.OK.title)
                    {
                        val newsId = it.payload.title.id
                        val title = it.payload.title.text.fromHtml()
                        val content = it.payload.content.fromHtml()
                        val date = Date(it.payload.title.publicationDate.milliseconds)
                        val news = News(newsId, title, content, date)
                        Flowable.just(news)
                    }
                    else
                    {
                        Flowable.empty()
                    }
                }
    }

    override fun saveNews(news: List<News>)
    {
        // Not required
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
                    News(it.id, it.text.fromHtml(), null, publicationDate)
                }
                .toList()
                .toFlowable()
    }
}