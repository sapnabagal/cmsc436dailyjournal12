package com.example.dailyjournal

import android.net.Uri
import java.time.LocalDate

class AudioType(audioUri: Uri, filename: String, date: LocalDate): ListItem{

    var dateData = date
    var audioData = audioUri
    var audioFile = filename

    override fun getListItemType(): Int {
        return ListItem.TYPE_AUDIO
    }

    override fun getDate(): LocalDate {
        return dateData
    }


}