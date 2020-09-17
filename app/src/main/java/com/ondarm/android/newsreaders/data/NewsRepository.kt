package com.ondarm.android.newsreaders.data

import kotlinx.coroutines.flow.Flow

/**
 * */
class NewsRepository(
    private val remoteNewsData: RemoteNewsData
): DataSource {
    override fun getAllNews(): List<News> {
        return remoteNewsData.getAllNews()
    }

}
