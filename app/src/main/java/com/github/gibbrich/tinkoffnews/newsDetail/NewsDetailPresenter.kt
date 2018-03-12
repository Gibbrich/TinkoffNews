package com.github.gibbrich.tinkoffnews.newsDetail

import com.github.gibbrich.tinkoffnews.data.INewsSource
import com.github.gibbrich.tinkoffnews.data.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Артур on 10.03.2018.
 */
class NewsDetailPresenter(
        private val newsSource: INewsSource,
        private val view: INewsDetailContract.View,
        private val newsId: Int
): INewsDetailContract.Presenter
{
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun subscribe()
    {
        view.setNewsLoadErrorVisible(false)

        loadNewsItem(newsId)
    }

    override fun unsubscribe()
    {
        disposables.clear()
    }

    override fun loadNewsItem(id: Int)
    {
        view.hideNewsContent()
        view.setLoadingIndicatorVisible(true)

        disposables.clear()

        val disposable = newsSource.getNewsItem(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { view.setLoadingIndicatorVisible(false) }
                .subscribe(
                        { view.showNewsContent(it.title, it.content!!) },
                        { view.setNewsLoadErrorVisible(true) }
                )
        disposables.add(disposable)
    }
}