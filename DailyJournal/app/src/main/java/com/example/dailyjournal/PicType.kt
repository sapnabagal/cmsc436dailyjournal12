package com.example.dailyjournal

import android.net.Uri

class PicType(imageUri: Uri): ListItem{

    var image : Uri = imageUri

    override fun getListItemType(): Int {
        return ListItem.TYPE_PIC
    }


}
