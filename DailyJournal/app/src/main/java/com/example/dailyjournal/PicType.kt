package com.example.dailyjournal

class PicType(imageResource: Int): ListItem{

    var image : Int = imageResource

    override fun getListItemType(): Int {
        return ListItem.TYPE_PIC
    }


}
