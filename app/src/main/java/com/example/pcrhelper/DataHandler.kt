package com.example.pcrhelper

import com.example.pcrsplitboxforandroid.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit

object DataHandler {

    private const val baseUrl: String = "https://www.caimogu.cc"
    private const val iconUrl: String = "/gzlj/data/icon?date=&lang=zh-cn"
    private const val dataUrl: String = "/gzlj/data?date=&lang=zh-ch"

    suspend fun requestDataAndSave(): Boolean {
        return withContext(Dispatchers.IO) {
            var icon = ""
            var conn: HttpURLConnection? = null
            try {
                conn = URL(baseUrl + iconUrl).openConnection() as HttpURLConnection
                conn.connect()
                conn.inputStream.use { input ->
                    val data = input.bufferedReader().readText()
                    icon += data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                conn?.disconnect()
            }
            TimeUnit.SECONDS.sleep(1)
            if (icon.isNotEmpty()) {
                ConfigurationDatabase
                    .getInstance(MainActivity.context)
                    .getConfigurationDao()
                    .insertConfig(Configuration(title = "icon", content = icon))
                return@withContext true
            }
            else {
                return@withContext false
            }
//            TimeUnit.SECONDS.sleep(1)
//            val data: String = URL(baseUrl + dataUrl).readText()
        }
    }

}