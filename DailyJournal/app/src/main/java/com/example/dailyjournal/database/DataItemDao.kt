package com.example.dailyjournal.database

import android.net.Uri
import androidx.room.*
import com.example.dailyjournal.*
import java.time.LocalDate

@Dao
abstract class DataItemDao {
    @Query("SELECT * FROM dataitem")
    abstract fun getAll(): List<DataItem>

    @Query("SELECT * FROM dataitem WHERE uid IN (:dataItemIds)")
    abstract fun loadAllByIds(dataItemIds: IntArray): List<DataItem>

    @Query("SELECT * FROM dataitem WHERE date == (:date)")
    abstract fun loadAllByDate(date: LocalDate): List<DataItem>

    fun loadAllByDateListItem(date: LocalDate): List<ListItem> {
        val l = loadAllByDate(date)
        val outList = mutableListOf<ListItem>()
        l.forEach { outList.add(Converters.dataItemToListItem(it)) }
        return outList
    }

    @Insert
    abstract fun insertAll(vararg dataItems: DataItem)

    fun insertAll(items: List<ListItem>) {
        val outList = mutableListOf<DataItem>()
        items.forEach { outList.add(Converters.listItemToDataItem(it)) }
        insertAll(*outList.toTypedArray())
    }

    fun insertAll(vararg items: ListItem) {
        insertAll(items.toList())
    }

    @Update
    abstract fun updateItems(vararg dataItems: DataItem)

    fun updateItems(items: List<ListItem>) {
        val outList = mutableListOf<DataItem>()
        items.forEach { outList.add(Converters.listItemToDataItem(it)) }
        updateItems(*outList.toTypedArray())
    }

    fun updateItems(vararg items: ListItem) {
        updateItems(items.toList())
    }

    @Delete
    abstract fun delete(dataItem: DataItem)

    fun delete(listItem: ListItem) { delete(Converters.listItemToDataItem(listItem)) }

    @Query("SELECT EXISTS(SELECT 1 FROM dataitem WHERE date == (:date))")
    abstract fun dateExists(date: LocalDate): Boolean

    @Query("SELECT MAX(date) FROM dataitem")
    abstract fun dateMax(): LocalDate?

    @Query("SELECT MIN(date) FROM dataitem")
    abstract fun dateMin(): LocalDate?
}
