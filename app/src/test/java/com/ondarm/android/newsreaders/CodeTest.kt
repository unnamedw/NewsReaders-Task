package com.ondarm.android.newsreaders

import com.ondarm.android.newsreaders.data.News
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.hamcrest.CoreMatchers.startsWith
import org.junit.runners.model.TestClass
import org.xmlpull.v1.XmlPullParser
import java.io.InputStreamReader
import java.net.URL
import java.util.regex.Pattern
import kotlin.random.Random
import kotlin.system.measureTimeMillis



fun main() {

    /*val info = arrayOf(
     "java backend junior pizza 150",
     "python frontend senior chicken 210",
     "python frontend senior chicken 150",
     "cpp backend senior pizza 260",
     "java backend junior chicken 80",
     "python backend senior chicken 50").apply {
    }
    val query = arrayOf(
        "java and backend and junior and pizza 100",
        "python and frontend and senior and chicken 200",
        "cpp and - and senior and pizza 250",
        "- and backend and senior and - 150",
        "- and - and - and chicken 100",
        "- and - and - and - 150")*/

    val info = Array(300000) {
        randomInput("info")
    }

    val query = Array(100) {
        randomInput("query")
    }

    val time = measureTimeMillis {
        solution(info, query)
    }

    val input1 = arrayOf("frodo", "fradi", "crodo", "abc123", "frodoc")
    val input2 = arrayOf("fr*d*", "abc1**")
    println(solution(input1, input2))


}

fun solution(user_id: Array<String>, banned_id: Array<String>): Int {
    var answer = 0
    val user_id_set = user_id.toMutableSet()

    banned_id.forEach { _banned_id ->
        val mPattern = Pattern.compile(toRegexStr(_banned_id))
        val tempSet = user_id_set.toSet()
        tempSet.forEach { _user_id ->
            val mMatcher = mPattern.matcher(_user_id)
            if (mMatcher.matches()) {
                answer++
                user_id_set.remove(_user_id)
            }
        }
    }

    return answer
}
fun toRegexStr(str: String): String {
    return str.map { char ->
        if (char == '*') '.' else char
    }.joinToString("")
}


//fun solution(info: Array<String>, query: Array<String>): IntArray {
//    var answer: IntArray = intArrayOf()
//    val volunteers = info.map { Volunteer(it) }
//    val qualifications = query.map { Qualification(it) }
//
//    answer = qualifications.map { qualification ->
//        var count = 0
//        volunteers.forEach { volunteer ->
//            if (
//                (volunteer.language == qualification.language || qualification.language == "-") &&
//                (volunteer.position == qualification.position || qualification.position == "-") &&
//                (volunteer.career == qualification.career || qualification.career == "-")&&
//                (volunteer.soulfood == qualification.soulfood || qualification.soulfood == "-") &&
//                (volunteer.grade.toInt() >= qualification.grade.toInt() || qualification.grade == "-"))
//                count++
//        }
//        count
//    }.toIntArray()
//
//    return answer
//}

class Volunteer(info: String) {
    val language: String
    val position: String
    val career: String
    val soulfood: String
    val grade: String

    init {
        val infoList = info.split(" ")
        language = infoList[0]
        position = infoList[1]
        career = infoList[2]
        soulfood = infoList[3]
        grade = infoList[4]
    }
}

class Qualification(query: String) {
    val language: String
    val position: String
    val career: String
    val soulfood: String
    val grade: String

    init {
        val queryList = query
            .replace("and ", "")
            .split(" ")
        language = queryList[0]
        position = queryList[1]
        career = queryList[2]
        soulfood = queryList[3]
        grade = queryList[4]
    }
}

fun randomInput(type: String): String {
    if (type == "info")
        return when (Random.nextInt(3)) {
            0 -> "java"
            1 -> "c++"
            else -> "python"
        } + " " + when(Random.nextInt(2)) {
            0 -> "backend"
            else -> "frontend"
        } + " " + when(Random.nextInt(2)) {
            0 -> "senior"
            else -> "junior"
        } + " " + when(Random.nextInt(2)) {
            0 -> "pizza"
            else -> "chicken"
        } + " " + (Random.nextInt(250)+1).toString()
    else
        return when (Random.nextInt(4)) {
            0 -> "java"
            1 -> "c++"
            2 -> "-"
            else -> "python"
        } + " and " + when(Random.nextInt(3)) {
            0 -> "backend"
            1 -> "-"
            else -> "frontend"
        }  + " and " + when (Random.nextInt(3)) {
            0 -> "senior"
            1 -> "junior"
            else -> "-"
        }  + " and " + when(Random.nextInt(3)) {
            0 -> "pizza"
            1 -> "-"
            else -> "chicken"
        } + " " + (Random.nextInt(250)+1).toString()


    /*val language = if (type=="info") {
        when (Random.nextInt(3)) {
            0 -> "java"
            1 -> "c++"
            else -> "python"
        }
    } else {
        when (Random.nextInt(4)) {
            0 -> "java"
            1 -> "c++"
            2 -> "-"
            else -> "python"
        }
    }

    val position = if (type == "info") {
        when(Random.nextInt(2)) {
            0 -> "backend"
            else -> "frontend"
        }
    } else {
        when(Random.nextInt(3)) {
            0 -> "backend"
            1 -> "-"
            else -> "frontend"
        }
    }

    val career = if (type == "info") {
        when(Random.nextInt(2)) {
            0 -> "senior"
            else -> "junior"
        }
    } else {
        when (Random.nextInt(3)) {
            0 -> "senior"
            1 -> "junior"
            else -> "-"
        }
    }

    val soulfood = if (type == "info") {
        when(Random.nextInt(2)) {
            0 -> "pizza"
            else -> "chicken"
        }
    } else {
        when(Random.nextInt(3)) {
            0 -> "pizza"
            1 -> "-"
            else -> "chicken"
        }
    }

    val grade = if (type == "info") {
        (Random.nextInt(250)+1).toString()
    } else {
        when (Random.nextInt(2)) {
            0 -> (Random.nextInt(250)+1).toString()
            else -> "-"
        }
    }*/

}



/**
 * 1번
 * 1298ha..asd.asd_123das.
 * */
/*fun solution(new_id: String): String {
    var answer: String = ""
    var recommended_id = new_id
        .toLowerCase()
        .replace("[^a-z0-9\\-_.]".toRegex(), "")
        .replace("\\.+".toRegex(), ".")
        .replace("^\\.".toRegex(), "")
        .replace("\\.$".toRegex(), "")
        .run { if (isEmpty()) "a" else this }
        .run { if (length>15) substring(0, 15) else this }
        .replace("\\.$".toRegex(), "")

    while (recommended_id.length < 3) recommended_id += recommended_id.last()

    answer = recommended_id

    return answer
}*/








    fun networkRequest(url: String): String {
        val API_KEY = "Apikey 8cd5387551c5fe6b5b2c49af2c8cc388a98b40246e2e32c9a269c6fa24bc8121"
        val client = OkHttpClient()
        val request = Request.Builder()
//					.addHeader("authorization", API_KEY)
            .url(URL(url))
            .build(); //GET Request

        val response = client.newCall(request).execute();
        val message = InputStreamReader(response.body?.byteStream()).readText()
        return message
    }





