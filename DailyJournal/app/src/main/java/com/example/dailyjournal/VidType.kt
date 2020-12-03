package com.example.dailyjournal

import android.net.Uri

class VidType(imageUri: Uri): ListItem{

    var video : Uri = imageUri

    override fun getListItemType(): Int {
        return ListItem.TYPE_VIDEO
    }


}