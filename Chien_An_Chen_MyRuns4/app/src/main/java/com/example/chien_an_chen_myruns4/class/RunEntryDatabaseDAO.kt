package com.example.chien_an_chen_myruns4

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RunEntryDatabaseDAO{

    // Define how database will behave

    @Insert
    suspend fun insertRunEntry(runentry: RunEntry)

    // cannot be suspend since regular initilization requries
    @Query("SELECT * FROM runEntry_table")
    fun getAllEntries(): Flow<List<RunEntry>>


    @Query("DELETE FROM runEntry_table WHERE id = :key")
    suspend fun deleteEntry(key: Long)
}