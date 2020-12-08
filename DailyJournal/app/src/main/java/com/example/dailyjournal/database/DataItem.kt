package com.example.dailyjournal.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class DataItem(
        @PrimaryKey(autoGenerate = true) var uid: Int,
        val type: Int,
        val date: LocalDate,
        val time: LocalTime,
        @ColumnInfo(name = "text_or_uri") val textOrUri: String
)
