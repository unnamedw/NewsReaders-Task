package com.ondarm.android.newsreaders

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ondarm.android.newsreaders.model.News
import kotlinx.android.synthetic.main.activity_news_list.*
import org.jsoup.Jsoup
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import com.ondarm.android.newsreaders.viewmodel.NewsListViewModel


class NewsListActivity : AppCompatActivity() {
    val mNewsList = mutableListOf<News>()
    val mAdapter = NewsListAdapter(this, mNewsList)
    val currentTime = System.currentTimeMillis()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        news_list.layoutManager = LinearLayoutManager(this)
        news_list.adapter = mAdapter

//        val newsListViewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)
//        newsListViewModel.getAll().observe(this, Observer {
//            mNewsList.clear()
//            mNewsList.addAll(it)
//            mAdapter.notifyDataSetChanged()
//        })

        mAdapter.setOnNewsClickListener(OnNewsClickListener {
            val intent = Intent(this, NewsViewActivity::class.java)
            intent.putExtra("url", mAdapter.items[it].url)
            startActivity(intent)
        })

        UpdateNewsAsyncTask().execute()
    }

    @SuppressLint("StaticFieldLeak")
    inner class UpdateNewsAsyncTask: AsyncTask<String, News, MutableList<News>>() {

        override fun doInBackground(vararg params: String?): MutableList<News> {

            val newsUrls = mutableListOf<String>()
            val newsList = mutableListOf<News>()

            Log.d("NewsListActivityLog", "point1: ${System.currentTimeMillis()-currentTime}")
            // rss 페이지 가져오기
            val rssXMLDoc = Jsoup.connect("https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko").get() // 오래걸림 (0.7 ~ 1초)
            Log.d("NewsListActivityLog", "point1-1: ${System.currentTimeMillis()-currentTime}")
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

            Log.d("NewsListActivityLog", "point2: ${System.currentTimeMillis()-currentTime}")
            //각 url 별로 기사 추출
            for (count in 0 until newsUrls.size) {
                val newsUrl = newsUrls[count]

                val newsRunnable = Runnable {
                    try {
                        Log.d("NewsListActivityLog", "point3-1: ${System.currentTimeMillis()-currentTime}")
                        val doc = Jsoup.connect(newsUrl).get().head() // 오래걸림 (0.4초 이상)
                        Log.d("NewsListActivityLog", "point3-2: ${System.currentTimeMillis()-currentTime}")
                        val title = doc.select("meta[property=og:title]").first()?.attr("content")
                            ?: doc.select("title").first().html()
                        Log.d("NewsListActivityLog", "point3-3: ${System.currentTimeMillis()-currentTime}")
                        val image = doc.select("meta[property=og:image]").first()?.attr("content") ?: ""
                        Log.d("NewsListActivityLog", "point3-4: ${System.currentTimeMillis()-currentTime}")
                        val description = doc.select("meta[property=og:description]").first()?.attr("content")
                            ?: doc.select("description").first()?.text()
                            ?: doc.select("meta[name=description]").attr("content")
                        Log.d("NewsListActivityLog", "point3-5: ${System.currentTimeMillis()-currentTime}")
                        val news = News(
                            newsUrl,
                            title,
                            image,
                            description
                        )
                        Log.d("NewsListActivityLog", "point3-6: ${System.currentTimeMillis()-currentTime}")

                        Log.d("test1", "SuccessURL: $newsUrl \nCOUNT: $count, title: ${news.title} \nimage: ${news.image} \ndescription: ${news.description}" )
                        publishProgress(news)

                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.d("test3", "(nullUrl) $newsUrl")
                    }
                    Log.d("NewsListActivityLog", "$count: ${System.currentTimeMillis()-currentTime}")
                }

                Thread(newsRunnable).start()
            }

            return newsList
        }

        override fun onProgressUpdate(vararg values: News?) {
            mNewsList.add(values[0]!!)
            mAdapter.notifyItemInserted(mNewsList.size)
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: MutableList<News>) {
            Log.d("test1", "listSize: ${result.size}")
            super.onPostExecute(result)
        }

    }


    override fun onBackPressed() {
        val exitDialogBuilder = AlertDialog.Builder(this)
        exitDialogBuilder.setMessage("앱을 종료하시겠습니까?")
        exitDialogBuilder.setNegativeButton("아니오") { _, _ ->  }
        exitDialogBuilder.setPositiveButton("종료") { _, _ -> finish() }

        val exitDialog = exitDialogBuilder.create()
        exitDialog.show()
    }

}
