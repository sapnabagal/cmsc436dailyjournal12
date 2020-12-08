package com.example.dailyjournal

import android.view.View
import android.widget.TextView
import com.chauthai.swipereveallayout.SwipeRevealLayout

class ViewHolderAudio(itemView: View) : ViewHolder(itemView) {


    override fun bindType(item: ListItem) {

    }

    override fun getDelete(): View {
        return itemView.findViewById(R.id.Delete)
    }

    override fun getSwipeLayout(): SwipeRevealLayout {
        return (itemView.findViewById<SwipeRevealLayout>(R.id.swipe_layout))
    }

}