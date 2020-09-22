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
import java.util.concurrent.atomic.AtomicInteger
import java.util.regex.Pattern
import kotlin.math.sign
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue
import kotlin.system.measureTimeMillis

var count = 0
var set = mutableSetOf<Int>()

fun main() {


    repeat(300) {
        MyThread().start()
    }

    Thread.sleep(1000)
    println("result ${MyThread.set.size}")



}
@Synchronized
fun increase(): Int {
    count++
    set.add(count)
    return count
}


class MyThread: Thread() {
    companion object {
        var count = 0
        var set = mutableSetOf<Int>()

        private fun increase(): Int = synchronized(this::class.java) {
            count++
            set.add(count)
            return count
        }

    }

    override fun run() {
        val num = increase()
        println("${currentThread().name}: "+num)
    }


}


