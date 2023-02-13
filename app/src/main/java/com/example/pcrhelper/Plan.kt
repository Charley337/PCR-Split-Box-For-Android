package com.example.pcrhelper

data class Plan(
    val h1: Homework,
    val h2: Homework,
    val h3: Homework,
    val borrow: List<String>
) {
    val damage: Int = h1.damage + h2.damage + h3.damage
    val sn: String = "${h1.sn}, ${h2.sn}, ${h3.sn}"
    val names: String = "${h1.nameList}, ${h2.nameList}, ${h3.nameList}"
    val score: Int

    init {
        var sum: Double = (Util.scoreRate[Util.snToKing(h1.sn)] ?: -100.0).times(h1.damage)
        sum = sum.plus((Util.scoreRate[Util.snToKing(h2.sn)] ?: -100.0).times(h2.damage))
        sum = sum.plus((Util.scoreRate[Util.snToKing(h3.sn)] ?: -100.0).times(h3.damage))
        score = sum.toInt()
    }
}