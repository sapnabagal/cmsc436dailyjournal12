package com.example.dailyjournal

import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class ViewHolderPic(itemView: View) : ViewHolder(itemView) {

    lateinit var image: ImageView
    init {
        image = itemView.findViewById<ImageView>(R.id.image)
    }
    override fun bindType(item: ListItem) {
        var userImage = item as PicType
        image.setImageURI(userImage.image)
    }

}