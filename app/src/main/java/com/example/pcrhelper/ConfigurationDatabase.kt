package com.example.pcrhelper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Configuration::class], version = 1, exportSchema = false)
abstract class ConfigurationDatabase : RoomDatabase() {
    abstract fun getConfigurationDao(): ConfigurationDao

    companion object {
        @Volatile
        private var instance: ConfigurationDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ConfigurationDatabase::class.java,
                "configs.db"
            ).allowMainThreadQueries().build()
    }
}