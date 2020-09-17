package com.ondarm.android.newsreaders.data

import kotlinx.coroutines.flow.Flow


interface DataSource {
    fun getAllNews():List<News>
}