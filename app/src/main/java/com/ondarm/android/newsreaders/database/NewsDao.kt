package com.ondarm.android.newsreaders.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ondarm.android.newsreaders.model.News

@Dao
interface NewsDao {
    @Query("SELECT * FROM News")
    fun getAll(): LiveData<List<News>>

    @Insert
    fun insert(news: News)

    @Update
    fun update(news: News)

    @Delete
    fun delete(news: News)

}