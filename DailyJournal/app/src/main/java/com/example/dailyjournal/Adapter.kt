package com.example.dailyjournal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_image.view.*
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.chauthai.swipereveallayout.SwipeRevealLayout

class Adapter(private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<ViewHolder>() {
    val dataList= mutableListOf<ListItem>()
    val binderHelper : ViewBinderHelper = ViewBinderHelper()


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
        binderHelper.bind(holder.getSwipeLayout(), currentItem.hashCode().toString())
        holder.bindType(currentItem)
        holder.getDelete().setOnClickListener{
            itemClickListener.onItemDelete(currentItem)
        }
        holder.edit.setOnClickListener{
            itemClickListener.onItemEdit(currentItem)
        }
        holder.click.setOnClickListener{
            if(holder.getSwipeLayout().isClosed){
                itemClickListener.onItemClick(currentItem)

            }
        }
        //holder.itemView.setOnClickListener{
        //    itemClickListener.onItemClick(currentItem)
        //}
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

}