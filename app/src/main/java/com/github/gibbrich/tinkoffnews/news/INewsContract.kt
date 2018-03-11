package com.github.gibbrich.tinkoffnews.news

import com.github.gibbrich.tinkoffnews.IBasePresenter
import com.github.gibbrich.tinkoffnews.IBaseView
import com.github.gibbrich.tinkoffnews.data.News

/**
 * Created by Артур on 09.03.2018.
 */
interface INewsContract
{
    interface Presenter : IBasePresenter
    {
        fun loadNews(isForceUpdate: Boolean)
        fun openNewsDetails(news: News)
    }

    interface View : IBaseView<Presenter>
    {
        fun setEmptyNewsVisible(isVisible: Boolean)
        fun showNews(news: List<News>)
        fun setLoadingIndicator(isLoading: Boolean)
        fun showLoadingNewsError()
        fun showNewsDetails(newsId: Int)
    }
}