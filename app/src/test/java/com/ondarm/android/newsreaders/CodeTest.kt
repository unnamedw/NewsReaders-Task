package com.ondarm.android.newsreaders

import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.FileReader
import java.io.FileWriter


fun main() {

    //        val file = File("myFile.txt")
//        lateinit var fw:FileWriter
//        val text = "This is TEST string."
//
//        try {
//            fw = FileWriter(file)
//            fw.use {
//                it.write(text)
//            }
//
//        } catch (e: Exception) {
//            e.printStackTrace() ;
//        }

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
    println(jsonArray.length())


}



//    val newsUrl = "https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko"
//
//    val connection = Jsoup.connect(newsUrl) // 오래걸림 (0.4초 이상)
//    val doc = withContext(Dispatchers.IO) {
//        val doc = connection.get()
//    }
//
//    val title = doc.select("meta[property=og:title]").first()?.attr("content")
//        ?: doc.select("title").first().html()


//    val image = doc.select("meta[property=og:image]").first()?.attr("content") ?: ""
//    val description = doc.select("meta[property=og:description]").first()?.attr("content")
//        ?: doc.select("description").first()?.text()
//        ?: doc.select("meta[name=description]").attr("content")
//
//    val news = News(
//        newsUrl,
//        title,
//        image,
//        description
//    )


