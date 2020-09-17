package com.ondarm.android.newsreaders.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class RemoteNewsData(

): DataSource {

    @ExperimentalCoroutinesApi
    override fun getAllNews(): Flow<News> = flow {
        val newsList = mutableListOf<News>()
        val googleRssUrl = "https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko"
        val newsUrls = getUrlsFromRss(googleRssUrl)
        for (newsUrl in newsUrls) {
//            getNewsFromUrl(newsUrl)?.let { newsList.add(it) }
            getNewsFromUrl(newsUrl)?.let { emit(it) }
        }
    }.flowOn(Dispatchers.IO)

    // 기사 url 로부터 News 를 추출
    private fun getNewsFromUrl(newsUrl: String): News? {
        try {
            val doc = Jsoup.connect(newsUrl).get().head() // 오래걸림 (0.4초 이상)
            val title = doc.select("meta[property=og:title]").first()?.attr("content")
                ?: doc.select("title").first().html()
            val image = doc.select("meta[property=og:image]").first()?.attr("content") ?: ""
            val description = doc.select("meta[property=og:description]").first()?.attr("content")
                ?: doc.select("description").first()?.text()
                ?: doc.select("meta[name=description]").attr("content")
            return News(
                newsUrl,
                title,
                image,
                description
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    // rss 페이지로부터 기사 url 을 추출
    private fun getUrlsFromRss(rssUrl: String): MutableList<String> {
        val newsUrls = mutableListOf<String>()
        // XML 파싱을 위한 XML Parser
        val parser = Jsoup.connect(rssUrl).get().let { document ->
            val rssXML = document.html()
            val rssXmlStrReader = StringReader(rssXML)
            val factory = XmlPullParserFactory.newInstance().apply { isNamespaceAware }
            factory.newPullParser().apply { setInput(rssXmlStrReader) }
        }
        // 기사 url 추출
        var eventType = parser.eventType
        var isNewsAddress = false
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG ->
                    if (parser.depth>3 && parser.name=="link") {
                        isNewsAddress = true
                    }
                XmlPullParser.TEXT ->
                    if (isNewsAddress) {
                        newsUrls.add(parser.text)
                        isNewsAddress = false
                    }
            }
            eventType = parser.next()
        }
        return newsUrls
    }

}