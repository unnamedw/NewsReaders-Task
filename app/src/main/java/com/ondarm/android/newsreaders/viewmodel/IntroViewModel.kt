package com.ondarm.android.newsreaders.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IntroViewModel: ViewModel() {

    fun testFun() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            delay(2500)
            Log.d("main", Thread.currentThread().name)
        }

    }
}