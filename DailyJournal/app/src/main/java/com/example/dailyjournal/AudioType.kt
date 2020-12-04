package com.example.dailyjournal

import java.time.LocalDate

class AudioType(date: LocalDate): ListItem{

    var dateData = date

    override fun getListItemType(): Int {
        return ListItem.TYPE_AUDIO
    }

    override fun getDate(): LocalDate {
        return dateData
    }


}