package com.github.gibbrich.tinkoffnews.newsDetail

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.github.gibbrich.tinkoffnews.R

class NewsDetailActivity : AppCompatActivity(), INewsDetailContract.View
{
    private lateinit var presenter: INewsDetailContract.Presenter

    private lateinit var titleTextView: TextView
    private lateinit var contentTextView: TextView
    private lateinit var loadingNewsView: View
    private lateinit var loadingErrorView: View

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val newsId = intent.extras.getInt(EXTRA_NEWS_ID, DEFAULT_NEWS_ID)
        if (newsId == DEFAULT_NEWS_ID)
        {
            throw IllegalArgumentException("Intent must contain EXTRA_NEWS_ID")
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        titleTextView = findViewById(R.id.title)
        contentTextView = findViewById(R.id.content)
        loadingNewsView = findViewById(R.id.loadingNews)
        loadingErrorView = findViewById(R.id.loadingError)

        presenter = NewsDetailPresenter(this, newsId)
        presenter.subscribe()
    }

    override fun onPause()
    {
        super.onPause()

        presenter.unsubscribe()
    }

    override fun setNewsLoadErrorVisible(isVisible: Boolean)
    {
        setViewVisible(loadingErrorView, isVisible)
    }

    override fun setLoadingIndicatorVisible(isVisible: Boolean)
    {
        setViewVisible(loadingNewsView, isVisible)
    }

    override fun showNewsLoadSuccessfully(title: String, content: String)
    {
        titleTextView.text = title
        contentTextView.text = content
    }

    private fun setViewVisible(view: View, isVisible: Boolean)
    {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    companion object
    {
        private const val EXTRA_NEWS_ID = "EXTRA_NEWS_ID"
        private const val DEFAULT_NEWS_ID = -1

        fun getIntent(context: Context, newsId: Int): Intent
        {
            return Intent(context, NewsDetailActivity::class.java).apply {
                putExtra(EXTRA_NEWS_ID, newsId)
            }
        }
    }
}
