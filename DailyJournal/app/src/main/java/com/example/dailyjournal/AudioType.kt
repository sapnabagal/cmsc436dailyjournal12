package com.example.dailyjournal

import java.io.File
import java.time.LocalDate
import java.time.LocalTime

class AudioType(filename: String, date: LocalDate, time: LocalTime): ListItem{

    private var id: Int = 0
    var audioFile: String = filename
    var dateData: LocalDate = date
    var timeData: LocalTime = time

    override fun getId(): Int = id
    override fun setId(id: Int) { this.id = id }

    override fun getListItemType(): Int = ListItem.TYPE_AUDIO

    override fun getDate(): LocalDate = dateData

    override fun getTime(): LocalTime = timeData

    constructor(id: Int, filename: String, date: LocalDate, time: LocalTime) : this(filename, date, time) { this.id = id }
}
