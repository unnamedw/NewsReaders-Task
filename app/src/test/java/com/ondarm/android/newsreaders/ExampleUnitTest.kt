package com.ondarm.android.newsreaders

import org.junit.Test

import org.junit.Assert.*
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun assert_test() {
        runBlocking {
            withContext(Dispatchers.IO) {
                val file = File("testJson.txt")
                lateinit var fr: FileReader
                lateinit var jsonString: String

                try {
                    fr = FileReader(file)
                    fr.use {
                        jsonString = it.readText()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val jsonObject = JSONObject(jsonString)
                val jsonArray = jsonObject.getJSONArray("gladmfsodyt")
                for (i in 0 until jsonArray.length()) {
                    val obj = JSONObject(jsonArray.get(i).toString())
                    println(obj.get("value"))
                }

            }

        }
    }
}


