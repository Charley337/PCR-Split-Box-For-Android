package com.example.pcrhelper

import androidx.room.*

@Dao
interface ConfigurationDao {
    @Query("SELECT * FROM configurations")
    fun getAll(): List<Configuration>

    @Query("SELECT * FROM configurations WHERE title=:title")
    fun getAllByTitle(title: String): List<Configuration>

    @Query("DELETE FROM configurations WHERE title=:title")
    fun deleteByTitle(title: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConfig(config: Configuration)
}