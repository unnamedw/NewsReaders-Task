package com.ondarm.android.newsreaders

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ondarm.android.newsreaders.model.News
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.lang.Runnable
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext


class NewsListActivity : AppCompatActivity() {
    private val newsUrls = mutableListOf<String>()
    private val newsList = mutableListOf<News>()
    private val mNewsList = mutableListOf<News>()
    private val mAdapter = NewsListAdapter(this, mNewsList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)
        rv_news_list.layoutManager = LinearLayoutManager(this)
        rv_news_list.adapter = mAdapter


        mAdapter.setOnNewsClickListener(OnNewsClickListener {
            val intent = Intent(this, NewsViewActivity::class.java)
            intent.putExtra("url", mAdapter.items[it].url)
            startActivity(intent)
        })

        rv_news_list.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = (recyclerView.adapter as NewsListAdapter).itemCount
                if (lastVisibleItemPosition == itemTotalCount-1) {
                    Log.d("mainactivity", "도착! lastVisibleItemPosition=$lastVisibleItemPosition itemTotalCount=$itemTotalCount")
                } else {
                    Log.d("mainactivity", "lastVisibleItemPosition=$lastVisibleItemPosition itemTotalCount=$itemTotalCount")
                }
            }
        })

        val scope = CoroutineScope(Dispatchers.IO)
        val parsingJob = scope.launch {
            val googleNewsRssUrl = "https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko"
            val parser = getXmlParserFromUrl(googleNewsRssUrl)

            // xml 파싱 및 url 추출
            var eventType = parser.eventType
            var isNewsAddress = false
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    //시작태그인 경우
                    XmlPullParser.START_TAG ->
                        //깊이가 3보다 큰 link 태그라면 다음에 올 내용을 저장.
                        if (parser.depth>3 && parser.name=="link") {
                            isNewsAddress = true
                        }
                    //내용인 경우
                    XmlPullParser.TEXT ->
                        if (isNewsAddress) {
                            newsUrls.add(parser.text)
                            isNewsAddress = false
                        }
                }
                eventType = parser.next()
            }

            for (newsUrl in newsUrls) {
                getNewsFromUrl(newsUrl)
            }

            withContext(Dispatchers.Main) {

            }
        }

    }

    private var tempTime: Long = 0
    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime-tempTime<1000) finish()

        tempTime = currentTime
        Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
    }

    private fun getNewsFromUrl(newsUrl: String): News {
        // url 로부터 뉴스를 추출해 결과값 리턴
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
            return News(
                newsUrl,
                "",
                "",
                ""
            )
        }

    }

    private fun getXmlParserFromUrl(url: String): XmlPullParser {
        lateinit var result: XmlPullParser

        // rss 페이지 가져오기
        val rssXMLDoc = Jsoup.connect(url).get() // 오래걸림 (0.7 ~ 1초)
        val rssXMLString = rssXMLDoc.html()
        val rssXmlStrReader = StringReader(rssXMLString)
        val factory = XmlPullParserFactory.newInstance().apply { isNamespaceAware }
        val parser = factory.newPullParser().apply { setInput(rssXmlStrReader) }

        result = parser
        return result
    }




}
