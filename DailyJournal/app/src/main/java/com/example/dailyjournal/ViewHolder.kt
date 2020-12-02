package com.example.dailyjournal

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    abstract fun bindType(item : ListItem)



}