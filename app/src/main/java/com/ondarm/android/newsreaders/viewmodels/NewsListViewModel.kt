package com.ondarm.android.newsreaders.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ondarm.android.newsreaders.data.News
import com.ondarm.android.newsreaders.data.NewsRepository

class NewsListViewModel(
    private val repository: NewsRepository
): ViewModel() {

    private val _newsList: MutableLiveData<List<News>> = MutableLiveData(repository.requestRemoteNewsData())
    val newsList: LiveData<List<News>>
        get() = _newsList
}