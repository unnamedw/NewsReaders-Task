package com.ondarm.android.newsreaders

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.ondarm.android.newsreaders.model.News
import com.ondarm.android.newsreaders.viewmodel.IntroViewModel
import org.jsoup.Jsoup
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class IntroActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val introViewModel = ViewModelProvider(this).get(IntroViewModel::class.java)

        Thread(Runnable {
            val newsUrls = mutableListOf<String>()
            val newsList = mutableListOf<News>()

            // rss 페이지 가져오기
            val rssXMLDoc = Jsoup.connect("https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko").get() // 오래걸림 (0.7 ~ 1초)
            val rssXMLString = rssXMLDoc.html()

            val rssXmlStrReader = StringReader(rssXMLString)
            val factory = XmlPullParserFactory.newInstance().apply { isNamespaceAware }
            val parser = factory.newPullParser().apply { setInput(rssXmlStrReader) }

            // xml 파싱
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

            //각 url 별로 기사 추출
            for (count in 0 until 1) {
                val newsUrl = newsUrls[count]

                val newsRunnable = Runnable {
                    try {
                        val doc = Jsoup.connect(newsUrl).get().head() // 오래걸림 (0.4초 이상)
                        val title = doc.select("meta[property=og:title]").first()?.attr("content")
                            ?: doc.select("title").first().html()
                        val image = doc.select("meta[property=og:image]").first()?.attr("content") ?: ""
                        val description = doc.select("meta[property=og:description]").first()?.attr("content")
                            ?: doc.select("description").first()?.text()
                            ?: doc.select("meta[name=description]").attr("content")
                        val news = News(
                            newsUrl,
                            title,
                            image,
                            description
                        )

                        introViewModel.insert(news)

                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.d("test3", "(nullUrl) $newsUrl")
                    }

                }

                Thread(newsRunnable).start()
            }
        })

        /** 1.3초 대기 후 메인 뉴스화면으로 이동 **/
        val splashHandler = Handler()
        splashHandler.postDelayed(Runnable {
            startActivity(Intent(this, NewsListActivity::class.java))
            finish()
        },1300)



    }


}
