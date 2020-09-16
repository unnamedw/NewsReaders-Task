package com.ondarm.android.newsreaders.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ondarm.android.newsreaders.data.News
import com.ondarm.android.newsreaders.data.NewsRepository
import kotlinx.coroutines.*

class NewsListViewModel(
    private val repository: NewsRepository
): ViewModel() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private val _newsList = MutableLiveData<List<News>>()

    val newsList: LiveData<List<News>>
        get() = _newsList

    init {
        updateNewsData()
    }

    fun updateNewsData() {
        scope.launch {
            withContext(Dispatchers.IO) {
                _newsList.value = repository.getAllNews()
            }
        }
    }
}