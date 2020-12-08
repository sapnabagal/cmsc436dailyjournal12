package com.example.dailyjournal

import android.net.Uri
import java.time.LocalDate
import java.time.LocalTime

/* Class to represent Image ListItem type.
  Contains local timestamp of creation, date attached to, and Uri content */
class PicType(imageUri: Uri, date: LocalDate, time: LocalTime): ListItem {

    private var id: Int = 0
    var image: Uri = imageUri
    var dateData: LocalDate = date
    var timeData: LocalTime = time

    override fun getId(): Int = id
    override fun setId(id: Int) { this.id = id }

    override fun getListItemType(): Int = ListItem.TYPE_PIC

    override fun getDate(): LocalDate = dateData

    override fun getTime(): LocalTime = timeData

    constructor(id: Int, imageUri: Uri, date: LocalDate, time: LocalTime) : this(imageUri, date, time) { this.id = id }
}
