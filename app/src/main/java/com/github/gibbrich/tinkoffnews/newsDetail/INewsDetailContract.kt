package com.github.gibbrich.tinkoffnews.newsDetail

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
        fun setNewsLoadErrorVisible(isVisible: Boolean)
        fun setLoadingIndicatorVisible(isVisible: Boolean)
        fun showNewsLoadSuccessfully(title: String, content: String)
    }
}