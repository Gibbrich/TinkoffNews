package com.github.gibbrich.tinkoffnews.news

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.github.gibbrich.tinkoffnews.R
import com.github.gibbrich.tinkoffnews.data.News
import com.github.gibbrich.tinkoffnews.data.NewsLocalSource
import com.github.gibbrich.tinkoffnews.data.NewsRemoteSource
import com.github.gibbrich.tinkoffnews.data.NewsRepository
import com.github.gibbrich.tinkoffnews.newsDetail.NewsDetailActivity
import com.github.gibbrich.tinkoffnews.utils.ScrollChildSwipeRefreshLayout

class MainActivity : AppCompatActivity(), INewsContract.View
{
    private lateinit var presenter: INewsContract.Presenter

    private lateinit var swipeRefreshLayout: ScrollChildSwipeRefreshLayout
    private lateinit var emptyNewsView: View
    private lateinit var newsList: RecyclerView
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRepository = NewsRepository.getInstance(NewsLocalSource, NewsRemoteSource)
        presenter = NewsPresenter(newsRepository, this)

        newsList = findViewById(R.id.newsList)
        newsList.setHasFixedSize(true)
        newsList.isClickable = true

        val layoutManager = LinearLayoutManager(this)
        newsList.layoutManager = layoutManager

        adapter = NewsAdapter { presenter.openNewsDetails(it) }
        newsList.adapter = adapter

        swipeRefreshLayout = findViewById(R.id.refreshLayout)
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        )
        swipeRefreshLayout.setOnRefreshListener { presenter.loadNews(true) }
        swipeRefreshLayout.setScrollUpChild(newsList)

        emptyNewsView = findViewById(R.id.emptyNews)

        presenter.subscribe()
    }

    override fun onPause()
    {
        super.onPause()

        presenter.unsubscribe()
    }

    override fun setEmptyNewsVisible(isVisible: Boolean)
    {
        emptyNewsView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun showNews(news: List<News>)
    {
        adapter.news = news
    }

    override fun setLoadingIndicator(isLoading: Boolean)
    {
        swipeRefreshLayout.isRefreshing = isLoading
    }

    override fun showLoadingNewsError()
    {
        Toast.makeText(this, R.string.loading_error, Toast.LENGTH_SHORT).show()
    }

    override fun showNewsDetails(newsId: Int)
    {
        val intent = NewsDetailActivity.getIntent(this, newsId)
        startActivity(intent)
    }
}
