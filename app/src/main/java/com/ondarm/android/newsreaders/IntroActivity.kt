package com.ondarm.android.newsreaders

import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ondarm.android.newsreaders.viewmodel.IntroViewModel
import kotlinx.coroutines.*
import java.io.*
import java.lang.Runnable

class IntroActivity : AppCompatActivity() {
    lateinit var introViewModel: IntroViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        introViewModel= ViewModelProvider(this).get(IntroViewModel::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            launch {


            }
        }

        var inputStream: InputStream? = null
        try{
            val assetManager = applicationContext.assets
            Log.d("json", "1")
            inputStream = assetManager.open("test.json")
            Log.d("json", "2")
            //파일읽기
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            //읽은내용 출력
            Log.d("json", "결과: $jsonString")
        } catch (e: IOException){
            e.printStackTrace();
        } finally {
            inputStream?.close()
        }





        /** 1.3초 대기 후 메인 뉴스화면으로 이동 **/
        val splashHandler = Handler()
        splashHandler.postDelayed(Runnable {
            startActivity(Intent(this, NewsListActivity::class.java))
            finish()
        },1300)



    }


}
