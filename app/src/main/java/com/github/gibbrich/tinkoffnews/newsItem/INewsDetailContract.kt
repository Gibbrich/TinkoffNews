package com.github.gibbrich.tinkoffnews.newsItem

import com.github.gibbrich.tinkoffnews.IBasePresenter
import com.github.gibbrich.tinkoffnews.IBaseView

/**
 * Created by Артур on 10.03.2018.
 */
interface INewsDetailContract
{
    interface Presenter: IBasePresenter
    {
        fun loadNewsItem(id: Int)
    }

    interface View: IBaseView<Presenter>
    {
        fun showNewsLoadError()
        fun showNewsLoadSuccessfully(title: String, content: String)
    }
}