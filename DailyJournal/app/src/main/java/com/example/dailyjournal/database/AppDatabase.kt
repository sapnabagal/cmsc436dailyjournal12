package com.example.dailyjournal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = arrayOf(DataItem::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    private val DB_NAME = "appDatabase.db"

    @Volatile
    private var instance: AppDatabase? = null

    @Synchronized
    open fun getInstance(context: Context): AppDatabase? {
        if (instance == null) { instance = create(context) }

        return instance
    }

    private fun AppDatabase() {}

    private fun create(context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()

    abstract fun dataItemDao(): DataItemDao
}
