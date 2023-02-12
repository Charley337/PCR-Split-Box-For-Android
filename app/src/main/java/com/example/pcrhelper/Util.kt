package com.example.pcrhelper

import org.json.JSONException
import org.json.JSONObject

object Util {

    val scoreRate: Map<String, Double> = mapOf(
        "A1" to 1.2,
        "A2" to 1.2,
        "A3" to 1.3,
        "A4" to 1.4,
        "A5" to 1.5,
        "B1" to 1.6,
        "B2" to 1.6,
        "B3" to 1.8,
        "B4" to 1.9,
        "B5" to 2.0,
        "C1" to 2.0,
        "C2" to 2.0,
        "C3" to 2.4,
        "C4" to 2.4,
        "C5" to 2.6,
        "D1" to 3.5,
        "D2" to 3.5,
        "D3" to 3.7,
        "D4" to 3.8,
        "D5" to 4.0,
        "E1" to 3.5,
        "E2" to 3.5,
        "E3" to 3.7,
        "E4" to 3.8,
        "E5" to 4.0
    )
    const val sysName: String = "Ikaros"

    lateinit var banList: List<String>
    lateinit var lackList: List<String>

    init {

    }

    fun jsonStringToMap(jsonStringTemp: String): HashMap<String, Any> {
        try {
            val jsonObjectTemp = JSONObject(jsonStringTemp)
            val keys = jsonObjectTemp.keys()
            val jsonMapTemp: HashMap<String, Any> = HashMap()
            var key: String
            var value: Any
            while (keys.hasNext()) {
                key = keys.next()
                value = jsonObjectTemp[key] as Any
                jsonMapTemp[key] = value
            }
            return jsonMapTemp
        } catch (e: JSONException) {
            /*TODO: call exception handler*/
            return HashMap()
        }
    }

    fun jsonMapToString(jsonMapTemp: HashMap<String, Any>): String {
        return JSONObject(jsonMapTemp as Map<*, *>).toString()
    }

    fun sysPrint(content: String) {
        println("$sysName: $content")
    }

    fun snToKing(sn: String): String {
        val temp: Char = sn[1]
        val num: Char = if (temp == 'T' || temp == 'W') {
            sn[2]
        } else {
            temp
        }
        return "${sn[0]}$num"
    }

    fun feasible1(h1: Homework): Boolean {
        val nameSet: HashSet<String> = HashSet()
        h1.nameList.forEach {
            if (!lackList.contains(it)) {
                nameSet.add(it)
            }
        }
        return nameSet.size >= 4
    }

    fun feasible2(h1: Homework, h2: Homework): Boolean {
        if (!feasible1(h1) || !feasible1(h2)) {
            return false
        }
        val nameSet: HashSet<String> = HashSet()
        h1.nameList.forEach {
            if (!lackList.contains(it)) {
                nameSet.add(it)
            }
        }
        h2.nameList.forEach {
            if(!lackList.contains(it)) {
                nameSet.add(it)
            }
        }
        return nameSet.size >= 8
    }

    fun feasible3(h1: Homework, h2: Homework, h3: Homework): List<Any> {
        if (!feasible2(h1, h2) || !feasible2(h1, h3) || !feasible2(h2, h3)) {
            return listOf(false, emptyList<String>())
        }
        val nameSet: HashSet<String> = HashSet()
        val borrow: MutableList<String> = mutableListOf()
        h1.nameList.forEach {
            nameSet.add(it)
        }
        h2.nameList.forEach {
            if (nameSet.contains(it)) {
                borrow.add(it)
            } else {
                nameSet.add(it)
            }
        }
        h3.nameList.forEach {
            if (nameSet.contains(it)) {
                borrow.add(it)
            } else {
                nameSet.add(it)
            }
        }
        nameSet.forEach {
            if (lackList.contains(it)) {
                borrow.add(it)
            }
        }
        return if (borrow.size > 3) {
            listOf(false, emptyList<String>())
        } else {
            listOf(true, borrow as List<String>)
        }
    }

    fun debugMainFunction(): String {
        return "${123}"
    }

}
