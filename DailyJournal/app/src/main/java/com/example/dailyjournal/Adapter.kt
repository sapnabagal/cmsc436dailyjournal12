package com.example.dailyjournal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_image.view.*

class Adapter(private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<ViewHolder>() {
    val dataList= mutableListOf<ListItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when(viewType){
            ListItem.TYPE_PIC ->{
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.add_image, parent, false)
                return ViewHolderPic(itemView)

            }
            ListItem.TYPE_AUDIO->{
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.add_audio, parent, false)
                return ViewHolderAudio(itemView)

            }
            ListItem.TYPE_VIDEO->{
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.add_video, parent, false)
                return ViewHolderPic(itemView)

            }
            else -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.add_text, parent, false)
                return ViewHolderText(itemView)
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].getListItemType()

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]
        //holder.imageView.setImageResource(currentItem.imageResource)
        //holder.textView.text = currentItem.text1
        holder.bindType(currentItem)
        holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(currentItem)
        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

}