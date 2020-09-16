package com.ondarm.android.newsreaders.data

/**
 * */
class NewsRepository(
    private val remoteNewsData: RemoteNewsData
): DataSource {
    override fun getAllNews(): List<News> {
        return remoteNewsData.getAllNews()
    }

}
