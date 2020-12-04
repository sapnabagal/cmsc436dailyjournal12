package com.example.dailyjournal

import android.net.Uri
import java.time.LocalDate

class PicType(imageUri: Uri, date: LocalDate): ListItem{

    var image : Uri = imageUri
    var dateData: LocalDate = date

    override fun getListItemType(): Int {
        return ListItem.TYPE_PIC
    }

    override fun getDate(): LocalDate {
        return dateData
    }


}
