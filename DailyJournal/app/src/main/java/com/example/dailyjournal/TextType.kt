package com.example.dailyjournal

class TextType(text : String): ListItem{

    var inputText: String = text

    override fun getListItemType(): Int {
        return ListItem.TYPE_TEXT
    }


}
