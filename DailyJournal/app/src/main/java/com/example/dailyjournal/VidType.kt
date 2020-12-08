package com.example.dailyjournal

import android.net.Uri
import java.time.LocalDate
import java.time.LocalTime

/* Class to represent Video ListItem type.
  Contains local timestamp of creation, date attached to, and Uri content */
class VidType(videoUri: Uri, date: LocalDate, time: LocalTime): ListItem{

    private var id: Int = 0
    var video: Uri = videoUri
    var dateData: LocalDate = date
    var timeData: LocalTime = time

    override fun getId(): Int = id
    override fun setId(id: Int) { this.id = id }

    override fun getListItemType(): Int = ListItem.TYPE_VIDEO

    override fun getDate(): LocalDate = dateData

    override fun getTime(): LocalTime = timeData

    constructor(id: Int, videoUri: Uri, date: LocalDate, time: LocalTime) : this(videoUri, date, time) { this.id = id }
}
