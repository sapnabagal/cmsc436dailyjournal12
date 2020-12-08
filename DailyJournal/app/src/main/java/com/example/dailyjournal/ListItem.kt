package com.example.dailyjournal

import com.example.dailyjournal.database.Converters
import com.example.dailyjournal.database.DataItemDao
import java.time.LocalDate
import java.time.LocalTime

interface ListItem {

    fun getId(): Int
    fun setId(id: Int)
    fun getListItemType(): Int
    fun getDate(): LocalDate
    fun getTime(): LocalTime

    fun initializeId(dataItemDao: DataItemDao) {
        val item = Converters.listItemToDataItem(this)
        dataItemDao.insertAll(item)
        setId(item.uid)
    }

    companion object {
        const val TYPE_AUDIO = 1
        const val TYPE_VIDEO = 2
        const val TYPE_TEXT = 3
        const val TYPE_PIC = 4
    }

}
