package com.example.dailyjournal

import android.net.Uri
import java.time.LocalDate

class VidType(imageUri: Uri, date : LocalDate): ListItem{

    var video : Uri = imageUri
    var dateData : LocalDate = date

    override fun getListItemType(): Int {
        return ListItem.TYPE_VIDEO
    }

    override fun getDate(): LocalDate {
        return dateData
    }


}