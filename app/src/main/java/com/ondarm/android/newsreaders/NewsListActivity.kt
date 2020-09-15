package com.ondarm.android.newsreaders

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ondarm.android.newsreaders.adapters.NewsListAdapter
import com.ondarm.android.newsreaders.listeners.OnNewsClickListener
import com.ondarm.android.newsreaders.data.News
import com.ondarm.android.newsreaders.databinding.ActivityNewsListBinding
import com.ondarm.android.newsreaders.viewmodels.NewsListViewModel
import com.ondarm.android.newsreaders.viewmodels.NewsListViewModelFactory
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class NewsListActivity : AppCompatActivity() {
    private val newsUrls = mutableListOf<String>()
    private val newsList = mutableListOf<News>()
    private val adapter = NewsListAdapter(this, newsList)
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)
//    private val viewModel: NewsListViewModel by viewModels { NewsListViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityNewsListBinding>(this, R.layout.activity_news_list)
            .apply {
                lifecycleOwner = this@NewsListActivity
            }

        // 뉴스 세팅
//        newsListViewModel

        // RecyclerView
        rv_news_list.layoutManager = LinearLayoutManager(this)
        rv_news_list.adapter = adapter

        // 뉴스 기사를 클릭하면 웹뷰를 통해 기사를 띄워준다.
        adapter.setOnNewsClickListener(OnNewsClickListener {
            val intent = Intent(this, NewsViewActivity::class.java)
            intent.putExtra("url", adapter.items[it].url)
            startActivity(intent)
        })

        // 페이징 처리
        rv_news_list.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val totalItemCount = (recyclerView.adapter as NewsListAdapter).itemCount
                val lastItemPosition = totalItemCount-1

                // 스크롤이 마지막 아이템에 도달하면 로그를 출력
                if (lastVisibleItemPosition == lastItemPosition) {
                    Log.d("mainactivity", "도착! lastVisibleItemPosition=$lastVisibleItemPosition itemTotalCount=$totalItemCount")
                } else {
                    Log.d("mainactivity", "lastVisibleItemPosition=$lastVisibleItemPosition itemTotalCount=$totalItemCount")
                }
            }
        })

        // 화면을 당겨서 새로고침 처리
        layout_refresh.setOnRefreshListener {
            Toast.makeText(this, "새로고침", Toast.LENGTH_SHORT).show()
            layout_refresh.isRefreshing = false
        }

    }

    // 뒤로가기를 두 번 누르면 종료처리
    private var tempTime: Long = 0
    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime-tempTime < 1000){
            finish()
        }
        tempTime = currentTime
        Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
    }

}
