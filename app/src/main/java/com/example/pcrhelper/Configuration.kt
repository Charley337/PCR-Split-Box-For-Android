package com.example.pcrhelper

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configurations")
data class Configuration(
    @PrimaryKey val title: String,
    @ColumnInfo(name = "content") val content: String?
)
