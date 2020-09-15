package com.ondarm.android.newsreaders.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * */
class NewsRepository(
    private val remoteNewsData: NewsRemoteData
) {
    fun requestRemoteNewsData(): List<News> = remoteNewsData.requestRemoteNewsData()
}
