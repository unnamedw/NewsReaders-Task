package com.ondarm.android.newsreaders.di

import com.ondarm.android.newsreaders.data.NewsRepository
import com.ondarm.android.newsreaders.data.RemoteNewsData
import com.ondarm.android.newsreaders.viewmodels.NewsListViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * 수동 종속성 삽입
 * */

@ExperimentalCoroutinesApi
object InjectorUtil {

    private fun getNewsRepository(): NewsRepository {
        return NewsRepository.getInstance(
            RemoteNewsData.getInstance()
        )
    }

    fun provideNewsListViewModelFactory(): NewsListViewModelFactory {
        val repository =
            getNewsRepository()
        return NewsListViewModelFactory(repository)
    }
}