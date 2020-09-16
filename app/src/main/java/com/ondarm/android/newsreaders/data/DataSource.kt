package com.ondarm.android.newsreaders.data

interface DataSource {
    fun getAllNews(): List<News>
}