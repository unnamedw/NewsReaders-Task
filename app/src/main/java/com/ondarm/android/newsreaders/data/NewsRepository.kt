package com.ondarm.android.newsreaders.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class NewsRepository  constructor(
    private val remoteNewsData: RemoteNewsData
): DataSource {

    override fun getAllNews(): Flow<News> {
        return remoteNewsData.getAllNews()
    }

    companion object {
        @Volatile private var instance: NewsRepository? = null

        fun getInstance(remoteNewsData: RemoteNewsData) =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(remoteNewsData).also { instance = it }
            }

    }

}
