package com.github.gibbrich.tinkoffnews.newsList

import com.github.gibbrich.tinkoffnews.data.INewsSource
import com.github.gibbrich.tinkoffnews.data.News
import com.github.gibbrich.tinkoffnews.data.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Артур on 09.03.2018.
 */
class NewsPresenter(private val view: INewsContract.View): INewsContract.Presenter
{
    // todo switch to DI
    private val newsRepository: INewsSource = NewsRepository
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun loadNews()
    {
        view.setLoadingIndicator(true)

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
    }

    override fun start()
    {
        loadNews()
    }

    private fun processNews(news: List<News>)
    {
        if (news.isEmpty())
        {
            view.showEmptyNews()
        }
        else
        {
            view.showNews(news)
        }
    }
}