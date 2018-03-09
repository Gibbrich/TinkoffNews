package com.github.gibbrich.tinkoffnews.api

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Артур on 09.03.2018.
 */
interface NewsAPI
{
    @GET("news")
    fun getNews(): Flowable<APINewsListResponse>

    @GET("news_content")
    fun getNewsData(@Query("id") id: Int): Flowable<APINewsItemResponse>
}