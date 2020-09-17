package com.ondarm.android.newsreaders.data

import kotlinx.coroutines.flow.Flow


interface DataSource {
    fun getAllNews():Flow<News>
}