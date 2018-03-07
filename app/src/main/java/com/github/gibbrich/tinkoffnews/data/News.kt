package com.github.gibbrich.tinkoffnews.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
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
{
}