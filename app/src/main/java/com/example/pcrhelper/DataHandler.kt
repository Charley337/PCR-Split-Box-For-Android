package com.example.pcrhelper

import com.example.pcrsplitboxforandroid.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
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
                    receivedData = if (encoding != null && encoding.contains("gzip")) {
                        GZIPInputStream(input).bufferedReader().readText()
                    } else if (encoding != null && encoding.contains("deflate")) {
                        DeflaterInputStream(input).bufferedReader().readText()
                    } else {
                        input.bufferedReader().readText()
                    }
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
        val dataRes: RequestResult = getByUrlAndSave(baseUrl = baseUrl, pathUrl = dataUrl, title = "data")
        if (dataRes.responseCode != 200) {
            return listOf(false, false, false)
        }

        val iconRes: RequestResult = getByUrlAndSave(baseUrl = baseUrl, pathUrl = iconUrl, title = "icon")
        if (iconRes.responseCode != 200) {
            return listOf(true, false, false)
        }

        val iconJsonObj = JSONObject(iconRes.receivedData)
        val idToName = JSONObject()
        try {
            val iconJsonData = iconJsonObj.getJSONArray("data")
            for (i in 0 until iconJsonData.length()) {
                for (j in 0 until iconJsonData.getJSONArray(i).length()) {
                    idToName.put(
                        iconJsonData.getJSONArray(i).getJSONObject(j).getString("id"),
                        iconJsonData.getJSONArray(i).getJSONObject(j).getString("iconValue")
                    )
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            return listOf(true, true, false)
        }
        ConfigurationDatabase
            .getInstance(MainActivity.context)
            .getConfigurationDao()
            .insertConfig(
                Configuration(title = "idToName", content = idToName.toString())
            )
        return listOf(true, true, true)
    }

    fun getHomeworksFromData(): Homeworks {
        var tempResultList = ConfigurationDatabase
            .getInstance(MainActivity.context)
            .getConfigurationDao()
            .getAllByTitle("data")
        if (tempResultList.isEmpty()) {
            println("Room database has not a key called \"data\"")
            return Homeworks(emptyList())
        }
        val data = JSONObject(tempResultList[0].content)
        tempResultList = ConfigurationDatabase
            .getInstance(MainActivity.context)
            .getConfigurationDao()
            .getAllByTitle("idToName")
        if (tempResultList.isEmpty()) {
            println("Room database has not a key called \"idToName\"")
            return Homeworks(emptyList())
        }
        val idToName = JSONObject(tempResultList[0].content)
        val homeworkList: MutableList<Homework> = mutableListOf()
        val snSet: HashSet<String> = HashSet()
        val temp1Data = data.getJSONArray("data")
        for (i in 0 until temp1Data.length()) {
            val temp2Homework = temp1Data.getJSONObject(i).getJSONArray("homework")
            for (j in 0 until temp2Homework.length()) {
                val temp3J = temp2Homework.getJSONObject(j)
                val sn = temp3J.getString("sn")
                if (snSet.contains(sn)) {
                    continue
                }
                snSet.add(sn)
                val name1 = idToName.getString(temp3J.getJSONArray("unit").getString(0))
                val name2 = idToName.getString(temp3J.getJSONArray("unit").getString(1))
                val name3 = idToName.getString(temp3J.getJSONArray("unit").getString(2))
                val name4 = idToName.getString(temp3J.getJSONArray("unit").getString(3))
                val name5 = idToName.getString(temp3J.getJSONArray("unit").getString(4))
                if (
                    Util.banList.contains(name1) ||
                    Util.banList.contains(name2) ||
                    Util.banList.contains(name3) ||
                    Util.banList.contains(name4) ||
                    Util.banList.contains(name5)
                ) {
                    continue
                }
                val damage = temp3J.getInt("damage")
                val auto = temp3J.getInt("auto")
                val remain = temp3J.getInt("remain")
                val video: MutableList<VideoInfo> = mutableListOf()
                val videoArr = temp3J.getJSONArray("video")
                for (k in 0 until videoArr.length()) {
                    video.add(
                        VideoInfo(
                            text = videoArr.getJSONObject(k).getString("text"),
                            url = videoArr.getJSONObject(k).getString("url")
                        )
                    )
                }
                homeworkList.add(
                    Homework(
                        name1 = name1,
                        name2 = name2,
                        name3 = name2,
                        name4 = name2,
                        name5 = name2,
                        auto = auto,
                        damage = damage,
                        remain = remain,
                        sn = sn,
                        video = video
                    )
                )
            }
        }
        return Homeworks(homeworkList)
    }

}