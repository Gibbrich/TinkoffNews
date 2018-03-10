package com.github.gibbrich.tinkoffnews.data

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*

/**
 * Created by Dvurechenskiyi on 07.03.2018.
 */
@Entity(tableName = "News")
class News(
        @PrimaryKey
        val id: Int,
        val title: String,
        val content: String?,
        val publicationDate: Date
)

@Dao
interface NewsDao
{
    @Query("SELECT id, title, content, publicationDate FROM News ORDER BY publicationDate DESC")
    fun getNewsHeaders(): Flowable<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<News>)

    @Query("SELECT id, title, content, publicationDate FROM News WHERE id = :id")
    fun getNewsItem(id: Int): Single<News>
}