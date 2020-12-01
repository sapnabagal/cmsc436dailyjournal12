package com.example.dailyjournal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_image.view.*

class Adapter() : RecyclerView.Adapter<Adapter.ViewHolder>() {
    val dataList= mutableListOf<TestData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.add_image, parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView.text = currentItem.text1
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView = itemView.image
        val textView = itemView.image_caption

        //holds references to row layout

    }
    override fun getItemCount(): Int {
        return dataList.size
    }

}