package com.example.dailyjournal

import java.time.LocalDate
import java.time.LocalTime

class TextType(text: String, date: LocalDate, time: LocalTime): ListItem{

    private var id: Int = 0
    var inputText: String = text
    var dateData : LocalDate = date
    var timeData: LocalTime = time

    override fun getId(): Int = id
    override fun setId(id: Int) { this.id = id }

    override fun getListItemType(): Int = ListItem.TYPE_TEXT

    override fun getDate(): LocalDate = dateData

    override fun getTime(): LocalTime = timeData

    constructor(id: Int, text: String, date: LocalDate, time: LocalTime) : this(text, date, time) { this.id = id }
}
