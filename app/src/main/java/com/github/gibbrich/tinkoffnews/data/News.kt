package com.github.gibbrich.tinkoffnews.data

import android.arch.persistence.room.*
import io.reactivex.Flowable
import java.util.*

/**
 * Created by Dvurechenskiyi on 07.03.2018.
 */
@Entity(tableName = "News")
class News(
        @PrimaryKey
        val id: Int,
        val text: String,
        val content: String?,
        val publicationDate: Date
)

@Dao
interface NewsDao
{
    @Query("SELECT id, text, publicationDate FROM News ORDER BY publicationDate DESC")
    fun getNewsHeaders(): Flowable<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<News>)
}