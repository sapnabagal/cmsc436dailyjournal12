package com.example.dailyjournal

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView

class ViewHolderVideo(itemView: View) : ViewHolder(itemView) {

    lateinit var video: VideoView
    init {
        video = itemView.findViewById<VideoView>(R.id.video)
    }
    override fun bindType(item: ListItem) {
        var userVideo = item as VidType
        video.setVideoURI(userVideo.video)
    }


}