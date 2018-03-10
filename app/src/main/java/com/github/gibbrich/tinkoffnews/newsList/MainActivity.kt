package com.github.gibbrich.tinkoffnews.newsList

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.github.gibbrich.tinkoffnews.R
import com.github.gibbrich.tinkoffnews.data.News
import com.github.gibbrich.tinkoffnews.newsItem.NewsDetailActivity
import java.util.ArrayList

class MainActivity : AppCompatActivity(), INewsContract.View
{
    private lateinit var presenter: INewsContract.Presenter

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var newsList: RecyclerView
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = NewsPresenter(this)

        newsList = findViewById(R.id.newsList)
        newsList.setHasFixedSize(true)
        newsList.isClickable = true

        val layoutManager = LinearLayoutManager(this)
        newsList.layoutManager = layoutManager

        val onItemClickListener: (News) -> Unit = { presenter.openNewsDetails(it) }

        adapter = NewsAdapter(ArrayList(), onItemClickListener)
        newsList.adapter = adapter

        swipeRefreshLayout = findViewById(R.id.refreshLayout)
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        )
        swipeRefreshLayout.setOnRefreshListener { presenter.loadNews() }

        presenter.subscribe()
    }

    override fun onPause()
    {
        super.onPause()

        presenter.unsubscribe()
    }

    override fun showEmptyNews()
    {
        // todo implement
        Toast.makeText(this, "Empty news stub showed", Toast.LENGTH_SHORT).show()
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
        // todo implement
        Toast.makeText(this, "Loading news error stub showed", Toast.LENGTH_SHORT).show()
    }

    override fun showNewsDetails(newsId: Int)
    {
        val intent = NewsDetailActivity.getIntent(this, newsId)
        startActivity(intent)
    }
}
