package com.ondarm.android.newsreaders

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.JsonObject
import org.json.JSONObject

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.io.InputStreamReader

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.ondarm.android.newsreaders", appContext.packageName)

        val assetManager = InstrumentationRegistry.getInstrumentation().targetContext.assets
        val jsonString = InputStreamReader(assetManager.open("test.json")).readText()

        if (jsonString.isNotEmpty()) {
            val jsonObject = JSONObject(jsonString)
            val jsonArray = jsonObject.getJSONArray("dev_atanasio")
            for (i in 0 until jsonArray.length()) {
                val obj = JSONObject(jsonArray[i].toString())
                if (obj.get("tagname")=="Lv") {
                    Log.d("UnitTest", obj.toString())
                }

            }
        }
    }
}
