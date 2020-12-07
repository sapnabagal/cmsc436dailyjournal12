package com.example.dailyjournal

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout


abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    public val edit :ImageView = itemView.findViewById(R.id.Edit)
    public val click:RelativeLayout= itemView.findViewById(R.id.front)
    abstract fun bindType(item : ListItem)
    abstract fun getDelete() : View
    abstract fun getSwipeLayout() : SwipeRevealLayout



}