package com.example.dailyjournal

import java.io.File
import java.time.LocalDate
import java.time.LocalTime

class AudioType(audioFile: File, date: LocalDate, time: LocalTime): ListItem{

    private var id: Int = 0
    var audio: File = audioFile
    var dateData = date
    var timeData: LocalTime = time

    override fun getId(): Int = id
    override fun setId(id: Int) { this.id = id }

    override fun getListItemType(): Int = ListItem.TYPE_VIDEO

    override fun getDate(): LocalDate = dateData

    override fun getTime(): LocalTime = timeData

    constructor(id: Int, audioFile: File, date: LocalDate, time: LocalTime) : this(audioFile, date, time) { this.id = id }
}
