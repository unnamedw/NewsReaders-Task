package com.ondarm.android.newsreaders

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Runnable
import java.util.*

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        /** 1.3초 대기 후 메인 뉴스화면으로 이동 **/
        val splashHandler = Handler()
        splashHandler.postDelayed(Runnable {
            startActivity(Intent(this, NewsListActivity::class.java))
            finish()
        },1300)

    }



}
