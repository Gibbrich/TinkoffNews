package com.github.gibbrich.tinkoffnews.newsItem

import com.github.gibbrich.tinkoffnews.data.INewsSource
import com.github.gibbrich.tinkoffnews.data.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Артур on 10.03.2018.
 */
class NewsDetailPresenter(
        private val view: INewsDetailContract.View,
        private val newsId: Int
): INewsDetailContract.Presenter
{
    // todo switch to DI
    private val newsSource: INewsSource = NewsRepository
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun subscribe()
    {
        loadNewsItem(newsId)
    }

    override fun unsubscribe()
    {
        disposables.clear()
    }

    override fun loadNewsItem(id: Int)
    {
        disposables.clear()

        val disposable = newsSource.getNewsItem(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { view.showNewsLoadSuccessfully(it.title, it.content!!) },
                        { view.showNewsLoadError() }
                )
        disposables.add(disposable)
    }
}