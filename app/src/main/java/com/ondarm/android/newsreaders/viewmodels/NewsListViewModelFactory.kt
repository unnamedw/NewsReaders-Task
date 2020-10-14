package com.ondarm.android.newsreaders.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ondarm.android.newsreaders.data.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Parameter 가 필요할 경우
 * */
@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class NewsListViewModelFactory (
    private val repository: NewsRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsListViewModel(repository) as T
    }
}