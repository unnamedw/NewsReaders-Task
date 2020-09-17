package com.ondarm.android.newsreaders

import com.ondarm.android.newsreaders.data.News
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.hamcrest.CoreMatchers.startsWith
import org.junit.runners.model.TestClass
import org.xmlpull.v1.XmlPullParser
import java.io.InputStreamReader
import java.net.URL
import java.util.regex.Pattern
import kotlin.random.Random
import kotlin.system.measureTimeMillis

suspend fun performRequest(request: Int): String {
    delay(1000) // imitate long-running asynchronous work
    return "response $request"
}

fun main() = runBlocking<Unit> {
    launch {
        repeat(6) {
            delay(500)
            println("!")
        }
    }
    listOf(1, 2, 3).asFlow() // a flow of requests
        .filter {
            it<2
        }
        .map { request -> performRequest(request) }
        .collect { response -> println(response) }
}


