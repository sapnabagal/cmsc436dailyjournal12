package com.example.dailyjournal

import android.view.View
import android.widget.TextView

class ViewHolderText(itemView: View) : ViewHolder(itemView) {

    lateinit var text : TextView
    init {
        text = itemView.findViewById(R.id.text_block)
    }
    override fun bindType(item: ListItem) {
        var userText = item as TextType
        text.setText(userText.inputText)
    }

}