package com.example.dailyjournal

import android.view.View
import android.widget.TextView
import com.chauthai.swipereveallayout.SwipeRevealLayout

class ViewHolderText(itemView: View) : ViewHolder(itemView) {

    lateinit var text : TextView
    init {
        text = itemView.findViewById(R.id.text_block)
    }
    override fun bindType(item: ListItem) {
        var userText = item as TextType
        text.setText(userText.inputText)
    }

    override fun getDelete(): View {
        return itemView.findViewById(R.id.Delete)
    }
    override fun getSwipeLayout(): SwipeRevealLayout {
        return (itemView.findViewById<SwipeRevealLayout>(R.id.swipe_layout))
    }

}