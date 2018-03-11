package com.github.gibbrich.tinkoffnews.news

import com.github.gibbrich.tinkoffnews.data.INewsSource
import com.github.gibbrich.tinkoffnews.data.News
import com.github.gibbrich.tinkoffnews.data.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Артур on 09.03.2018.
 */
class NewsPresenter(
        private val newsRepository: INewsSource,
        private val view: INewsContract.View
): INewsContract.Presenter
{
    private var isFirstLoad = true // first load after app startup
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun loadNews(isForceUpdate: Boolean)
    {
        view.setLoadingIndicator(true)

        if (isForceUpdate || !isFirstLoad)
        {
            newsRepository.refreshNews()
        }

        disposables.clear()

        val disposable = newsRepository.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { view.setLoadingIndicator(false) }
                .subscribe(
                        { processNews(it) },
                        { view.showLoadingNewsError() }
                )
        disposables.add(disposable)
        isFirstLoad = false
    }

    override fun subscribe()
    {
        loadNews(false)
    }

    override fun unsubscribe()
    {
        disposables.clear()
    }

    override fun openNewsDetails(news: News)
    {
        view.showNewsDetails(news.id)
    }

    private fun processNews(news: List<News>)
    {
        view.setEmptyNewsVisible(news.isEmpty())

        if (!news.isEmpty())
        {
            view.showNews(news)
        }
    }
}