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
    val video: List<String>
) {
    val nameList: List<String> = listOf(name1, name2, name3, name4, name5)
}