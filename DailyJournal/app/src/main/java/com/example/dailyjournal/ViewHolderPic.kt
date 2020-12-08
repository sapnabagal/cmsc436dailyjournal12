package com.example.dailyjournal

import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chauthai.swipereveallayout.SwipeRevealLayout

/* Specific ViewHolder for Image type in recyclerView. */

class ViewHolderPic(itemView: View) : ViewHolder(itemView) {

    lateinit var image: ImageView
    init {
        image = itemView.findViewById<ImageView>(R.id.image)
    }
    override fun bindType(item: ListItem) {
        var userImage = item as PicType
        image.setImageURI(userImage.image)
    }

    override fun getDelete(): View {
        return itemView.findViewById(R.id.Delete)
    }
    override fun getSwipeLayout(): SwipeRevealLayout {
        return (itemView.findViewById<SwipeRevealLayout>(R.id.swipe_layout))
    }

}