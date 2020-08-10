package com.ondarm.android.newsreaders

import androidx.annotation.IntegerRes
import androidx.annotation.Nullable

//fun main() {
//    println(solution(10))
//    println(solution(12))
//    println(solution(13))
//}

fun solution(x: Int): Boolean {
    val answer: Boolean
    var sum = 0
    var num = x

    do {
        sum += num%10
        num /= 10
    } while (num>0)

    answer = x%sum==0
    return answer
}