package com.ondarm.android.newsreaders

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.ondarm.android.newsreaders.data.RemoteNewsData
import com.ondarm.android.newsreaders.viewmodels.NewsListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import org.json.JSONArray
import org.json.JSONObject

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.io.InputStreamReader
import kotlin.system.measureTimeMillis

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class ExampleInstrumentedTest {

    @InternalCoroutinesApi
    @Test
    fun androidTest() {
        val json = """["1", "2등", "3"]"""
//        val json = "[1, 2, 3]"
        val a = JSONArray(json)
        Log.d("MyJson", "${a.get(1)}")
    }
}
