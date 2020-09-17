package com.ondarm.android.newsreaders

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService


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