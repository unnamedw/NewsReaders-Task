package com.ondarm.android.newsreaders.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.ondarm.android.newsreaders.database.AppDataBase
import com.ondarm.android.newsreaders.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IntroViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application, AppDataBase::class.java, "news-db"
    ).build()

    fun getAll(): LiveData<List<News>> {
        return db.newsDao().getAll()
    }

    fun insert(todo: News) = viewModelScope.launch(Dispatchers.IO) {
        db.newsDao().insert(todo)
    }
}