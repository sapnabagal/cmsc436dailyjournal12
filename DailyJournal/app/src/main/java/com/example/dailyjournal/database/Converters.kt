package com.example.dailyjournal.database

import android.net.Uri
import androidx.room.TypeConverter
import com.example.dailyjournal.*
import java.io.File
import java.lang.Exception
import java.time.LocalDate
import java.time.LocalTime

object Converters {
    @TypeConverter
    @JvmStatic
    fun dateEpochToDate(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    @JvmStatic
    fun dateToDateEpoch(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

    @TypeConverter
    @JvmStatic
    fun timeNanoToDate(value: Long?): LocalTime? {
        return value?.let { LocalTime.ofNanoOfDay(value) }
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimeNano(date: LocalTime?): Long? {
        return date?.toNanoOfDay()
    }

    fun listItemToDataItem(it: ListItem): DataItem {
        return when (it.getListItemType()) {
            ListItem.TYPE_AUDIO -> DataItem(it.getId(), it.getListItemType(), it.getDate(), it.getTime(), (it as AudioType).audioFile)
            ListItem.TYPE_VIDEO -> DataItem(it.getId(), it.getListItemType(), it.getDate(), it.getTime(), (it as VidType).video.toString())
            ListItem.TYPE_TEXT -> DataItem(it.getId(), it.getListItemType(), it.getDate(), it.getTime(), (it as TextType).inputText)
            ListItem.TYPE_PIC -> DataItem(it.getId(), it.getListItemType(), it.getDate(), it.getTime(), (it as PicType).image.toString())
            else -> throw Exception()
        }
    }

    fun dataItemToListItem(it: DataItem): ListItem {
        return when (it.type) {
            ListItem.TYPE_AUDIO -> AudioType(it.uid, it.textOrUri, it.date, it.time)
            ListItem.TYPE_VIDEO -> VidType(it.uid, Uri.parse(it.textOrUri), it.date, it.time)
            ListItem.TYPE_TEXT -> TextType(it.uid, it.textOrUri, it.date, it.time)
            ListItem.TYPE_PIC -> PicType(it.uid, Uri.parse(it.textOrUri), it.date, it.time)
            else -> throw Exception()
        }
    }
}
