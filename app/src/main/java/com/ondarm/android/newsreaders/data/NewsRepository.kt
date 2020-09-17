package com.ondarm.android.newsreaders.data

import kotlinx.coroutines.flow.Flow

/**
 * */
class NewsRepository(
    private val remoteNewsData: RemoteNewsData
): DataSource {
    override fun getAllNews(): Flow<News> {
        return remoteNewsData.getAllNews()
    }

}
