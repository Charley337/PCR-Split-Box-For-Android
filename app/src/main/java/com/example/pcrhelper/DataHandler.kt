package com.example.pcrhelper

import com.example.pcrsplitboxforandroid.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.DeflaterInputStream
import java.util.zip.GZIPInputStream

data class RequestResult(
    val responseCode: Int,
    val receivedData: String,
    val errorMessage: String
)

object DataHandler {

    private const val baseUrl: String = "https://www.caimogu.cc"
//    private const val iconUrl: String = "/gzlj/data/icon?date=&lang=zh-cn"
//    private const val dataUrl: String = "/gzlj/data?date=&lang=zh-ch"
    private const val iconUrl: String = "/gzlj/data/icon?date=2022-11&lang=zh-cn"
    private const val dataUrl: String = "/gzlj/data?date=2022-11&lang=zh-cn"

    private suspend fun getByUrlAndSave(baseUrl: String, pathUrl: String, title: String): RequestResult {
        return withContext(Dispatchers.IO) {
            var responseCode = 0
            var receivedData = ""
            var errorMessage = ""
            var conn: HttpURLConnection? = null
            try {
                conn = URL(baseUrl + pathUrl).openConnection() as HttpURLConnection
                conn.setRequestProperty("authority", "www.caimogu.cc")
                conn.requestMethod = "GET"
                conn.setRequestProperty("path", pathUrl)
                conn.setRequestProperty("scheme", "https")
                conn.setRequestProperty("accept", "*/*")
                conn.setRequestProperty("accept-encoding", "gzip, deflate")
                conn.setRequestProperty("accept-language", "zh-CN,zh;q=0.9")
                conn.setRequestProperty("cookie", "")
                conn.setRequestProperty("referer", "https://www.caimogu.cc/gzlj.html")
                conn.setRequestProperty("sec-ch-ua", "\"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"")
                conn.setRequestProperty("sec-ch-ua-mobile", "?1")
                conn.setRequestProperty("sec-ch-ua-platform", "\"Android\"")
                conn.setRequestProperty("sec-fetch-dest", "empty")
                conn.setRequestProperty("sec-fetch-mode", "cors")
                conn.setRequestProperty("sec-fetch-site", "same-origin")
                conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36")
                conn.setRequestProperty("x-requested-with", "XMLHttpRequest")
                responseCode = conn.responseCode
                conn.connect()
                conn.inputStream.use { input ->
                    val encoding: String? = conn.contentEncoding
                    val data: String = if (encoding != null && encoding.contains("gzip")) {
                        GZIPInputStream(input).bufferedReader().readText()
                    } else if (encoding != null && encoding.contains("deflate")) {
                        DeflaterInputStream(input).bufferedReader().readText()
                    } else {
                        input.bufferedReader().readText()
                    }
                    receivedData = data
                    println(data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = e.stackTraceToString()
            } finally {
                conn?.disconnect()
            }
            if (responseCode == 200) {
                ConfigurationDatabase
                    .getInstance(MainActivity.context)
                    .getConfigurationDao()
                    .insertConfig(Configuration(title = title, content = receivedData))
            }
            delay(200)
            return@withContext RequestResult(responseCode, receivedData, errorMessage)
        }
    }

    suspend fun getData(): List<Boolean> {
        val res1: RequestResult = getByUrlAndSave(baseUrl = baseUrl, pathUrl = dataUrl, title = "data")
        if (res1.responseCode != 200) {
            return listOf(false, false, false)
        }

        val res2: RequestResult = getByUrlAndSave(baseUrl = baseUrl, pathUrl = iconUrl, title = "icon")
        if (res2.responseCode != 200) {
            return listOf(true, false, false)
        }

        return listOf(true, true, false)
//        ConfigurationDatabase
//            .getInstance(MainActivity.context)
//            .getConfigurationDao()
//            .insertConfig(
//                Configuration(title = "idToName", content = "")
//            )
//        return listOf(true, true, true)
    }

}