package com.example.pcrhelper

class DataHandler {

    private val baseUrl: String = "https://www.caimogu.cc"
    private val iconUrl: String = "/gzlj/data/icon?date=&lang=zh-cn"
    private val dataUrl: String = "/gzlj/data?date=&lang=zh-ch"
    private val headers: HashMap<String, String> = HashMap()

    init {
        headers["authority"] = "www.caimogu.cc"
        headers["method"] = "GET"
        headers["path"] = "/gzlj/data?date=&lang=zh-cn"
        headers["scheme"] = "https"
        headers["accept"] = "*/*"
        headers["accept-encoding"] = "gzip, deflate, br"
        headers["accept-language"] = "zh-CN,zh;q=0.9"
        headers["cookie"] = ""
        headers["referer"] = "https://www.caimogu.cc/gzlj.html"
        headers["sec-ch-ua"] =
            "\"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\""
        headers["sec-ch-ua-mobile"] = "?0"
        headers["sec-ch-ua-platform"] = "\"Windows\""
        headers["sec-fetch-dest"] = "empty"
        headers["sec-fetch-mode"] = "cors"
        headers["sec-fetch-site"] = "same-origin"
        headers["user-agent"] = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36"
        headers["x-requested-with"] = "XMLHttpRequest"
    }



}