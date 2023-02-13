package com.example.pcrhelper

import com.example.pcrsplitboxforandroid.MainActivity
import java.net.URL
import java.util.concurrent.TimeUnit

object DataHandler {

    private const val baseUrl: String = "https://www.caimogu.cc"
    private const val iconUrl: String = "/gzlj/data/icon?date=&lang=zh-cn"
    private const val dataUrl: String = "/gzlj/data?date=&lang=zh-ch"

    fun requestDataAndSave() {
        val icon: String = URL(baseUrl + iconUrl).readText()
        ConfigurationDatabase
            .getInstance(MainActivity.context)
            .getConfigurationDao()
            .insertConfig(Configuration(title = "icon", content = icon))
        TimeUnit.SECONDS.sleep(1)
//        val data: String = URL(baseUrl + dataUrl).readText()
    }

}