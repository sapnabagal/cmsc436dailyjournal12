package com.example.dailyjournal

import java.time.LocalDate

class TextType(text : String, date: LocalDate): ListItem{

    var inputText: String = text
    var dateData : LocalDate = date

    override fun getListItemType(): Int {
        return ListItem.TYPE_TEXT
    }

    override fun getDate(): LocalDate {
        return dateData
    }


}
