package com.example.dailyjournal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = arrayOf(DataItem::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dataItemDao(): DataItemDao

    companion object {
        private val DB_NAME = "appDatabase.db"

        @Volatile
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) { instance = create(context) }

            return instance!!
        }

        private fun create(context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).allowMainThreadQueries().build()
    }
}
