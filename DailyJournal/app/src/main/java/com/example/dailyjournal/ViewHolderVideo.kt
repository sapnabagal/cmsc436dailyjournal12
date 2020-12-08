package com.example.dailyjournal

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import com.chauthai.swipereveallayout.SwipeRevealLayout

class ViewHolderVideo(itemView: View) : ViewHolder(itemView) {

    lateinit var video: VideoView
    init {
        video = itemView.findViewById<VideoView>(R.id.video)
    }
    override fun bindType(item: ListItem) {
        var userVideo = item as VidType
        video.setVideoURI(userVideo.video)
        video.seekTo(1);
    }

    override fun getDelete(): View {
        return itemView.findViewById(R.id.Delete)
    }
    override fun getSwipeLayout(): SwipeRevealLayout {
        return (itemView.findViewById<SwipeRevealLayout>(R.id.swipe_layout))
    }


}
