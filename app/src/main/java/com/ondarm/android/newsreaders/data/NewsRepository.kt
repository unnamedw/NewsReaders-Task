package com.ondarm.android.newsreaders.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * */
class NewsRepository(
    private val remoteNewsData: RemoteNewsData
): DataSource {
    @ExperimentalCoroutinesApi
    override fun getAllNews(): Flow<News> {
        return remoteNewsData.getAllNews()
    }

}
