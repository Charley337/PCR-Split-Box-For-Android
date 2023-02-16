package com.example.pcrhelper

data class Homework(
    val name1: String,
    val name2: String,
    val name3: String,
    val name4: String,
    val name5: String,
    val auto: Int,
    val damage: Int,
    val remain: Int,
    val sn: String,
    val video: List<VideoInfo>
) {
    val nameList: List<String> = listOf(name1, name2, name3, name4, name5)
}

data class VideoInfo(
    val text: String,
    val url: String
)

class Homeworks(homeworkList: List<Homework>) {

    val hws: List<Homework>
    val num: Int
    val ns1: Int
    val ns2: Int
    val ns3: Int
    val ns4: Int
    val ns5: Int

    init {
        var tempNs1: Int = 0
        var tempNs2: Int = 0
        var tempNs3: Int = 0
        var tempNs4: Int = 0
        var tempNs5: Int = 0
        hws = homeworkList.sortedBy { homework -> homework.sn }
        num = hws.size
        hws.forEach {
            when (it.sn[0]) {
                'A' -> tempNs1++
                'B' -> tempNs2++
                'C' -> tempNs3++
                'D' -> tempNs4++
                else -> tempNs5++
            }
        }
        ns1 = tempNs1
        ns2 = tempNs2
        ns3 = tempNs3
        ns4 = tempNs4
        ns5 = tempNs5
    }

    private fun getStage(stage: Char): List<Homework> {
        return when (stage) {
            'A' -> hws.subList(0, ns1)
            'B' -> hws.subList(ns1, ns1 + ns2)
            'C' -> hws.subList(ns1 + ns2, ns1 + ns2 + ns3)
            'D' -> hws.subList(ns1 + ns2 + ns3, ns1 + ns2 + ns3 + ns4)
            'E' -> hws.subList(ns1 + ns2 + ns3 + ns4, hws.size)
            else -> emptyList()
        }
    }

    fun getPlanList(
        stage: Char,
        sortKey: String = "score",
        reverse: Boolean = false,
        auto: Boolean = true
    ): List<Plan> {
        val homeworkList: List<Homework> = getStage(stage)
        val planList: MutableList<Plan> = mutableListOf()
        val length: Int = homeworkList.size
        for (i in 0 until length) {
            for (j in i + 1 until length) {
                for (k in j + 1 until length) {
                    if (
                        if (auto) {
                            (homeworkList[i].auto and homeworkList[j].auto and homeworkList[k].auto == 1) &&
                            (homeworkList[i].remain or homeworkList[j].remain or homeworkList[k].remain == 0)
                        } else {
                            homeworkList[i].remain or homeworkList[j].remain or homeworkList[k].remain == 0
                        }
                    ) {
                        val borrow: List<String>? = Util.feasible3(
                            homeworkList[i],
                            homeworkList[j],
                            homeworkList[k]
                        )
                        if (borrow != null) {
                            planList.add(
                                Plan(homeworkList[i], homeworkList[j], homeworkList[k], borrow)
                            )
                        }
                    }
                }
            }
        }
        if (reverse) {
            if (sortKey == "damage") {
                planList.sortByDescending { plan -> plan.damage }
            } else {
                planList.sortByDescending { plan -> plan.score }
            }
        } else {
            if (sortKey == "damage") {
                planList.sortBy { plan -> plan.damage }
            } else {
                planList.sortBy { plan -> plan.score }
            }
        }
        return planList
    }

}