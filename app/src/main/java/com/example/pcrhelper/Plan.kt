package com.example.pcrhelper

class Plan(
    val h1: Homework,
    val h2: Homework,
    val h3: Homework,
    val borrow: List<String>
) {
    val damage: Int = h1.damage + h2.damage + h3.damage
    val sn: String = "${h1.sn}, ${h2.sn}, ${h3.sn}"
    val names: String = "${h1.nameList}, ${h2.nameList}, ${h3.nameList}"
    val score: Int = 0
}