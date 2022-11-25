package com.example.chien_an_chen_myruns4

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "runEntry_table")
data class RunEntry(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "entry_type_column")
    var entryType: String = "",

    @ColumnInfo(name = "activity_type_column")
    var activityType: String = "",

    @ColumnInfo(name = "duration_column")
    var duration: Long = 0L,

    @ColumnInfo(name = "distance_column")
    var distance: Long = 0L,

    @ColumnInfo(name = "calories_column")
    var calories: Long = 0L,

    @ColumnInfo(name = "heart_rate_column")
    var heartRate: Long = 0L,

    @ColumnInfo(name = "comment_column")
    var comment: String = ""
)
