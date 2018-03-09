package com.github.gibbrich.tinkoffnews.newsList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.gibbrich.tinkoffnews.R
import com.github.gibbrich.tinkoffnews.data.News

/**
 * Created by Артур on 09.03.2018.
 */
class NewsAdapter(news: List<News>): RecyclerView.Adapter<NewsViewHolder>()
{
    var news = news
        set(value)
        {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int)
    {
        holder.title.text = news[position].text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder
    {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(root)
    }

    override fun getItemCount() = news.size
}

class NewsViewHolder(root: View): RecyclerView.ViewHolder(root)
{
    val title: TextView = root.findViewById(R.id.news_title)
}