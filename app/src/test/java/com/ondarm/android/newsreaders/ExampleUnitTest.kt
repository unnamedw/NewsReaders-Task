package com.ondarm.android.newsreaders

import com.ondarm.android.newsreaders.data.News
import org.junit.Test

//import org.junit.Assert.*
import kotlinx.coroutines.*
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.system.measureTimeMillis


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
//    @Test
    fun assert_test() {
        val input1 = arrayOf("frodo", "fradi", "crodo", "abc123", "frodoc")
        val input2 = arrayOf("*rodo", "*rodo", "******")
        println(solution(input1, input2))

    }

    fun solution(user_id: Array<String>, banned_id: Array<String>): Int {
        var answer = 0
        val matched_id = Array<MutableList<String>>(banned_id.size) { mutableListOf() }

        // 각각의 밴 id에 해당되는 id 들을 체크함
        banned_id.forEachIndexed { index, _banned_id ->
            val pattern = Pattern.compile(toRegexStr(_banned_id))
            user_id.forEach { _user_id ->
                val matcher = pattern.matcher(_user_id)
                if (matcher.matches()) {
                    matched_id[index].add(_user_id)
                }
            }
        }

        // DFS
        val lastDepth = matched_id.size-1 // 탐색의 마지막 깊이
        val resultSet = mutableSetOf<String>() // 중복여부를 체크하는 Set
        fun dfs(currentDepth: Int, beforeThis: List<String>) {
            if (currentDepth>lastDepth) { // 탐색이 완료되었다면 정렬한 뒤 문자열로 바꿔 Set 에 넣는다.
                beforeThis.sorted().joinToString(" ").let { resultSet.add(it) }
            } else {
                matched_id[currentDepth].forEach { // 이전 값이 중복되지 않을 경우 다음 깊이로 진행한다.
                    if (!beforeThis.contains(it)) dfs(currentDepth+1, beforeThis.toMutableList().apply { add(it) })
                }
            }
        }

        dfs(0, listOf())

        answer = resultSet.size
        return answer
    }
    fun toRegexStr(str: String): String {
        return str.map { char ->
            if (char == '*') '.' else char
        }.joinToString("")
    }

}


