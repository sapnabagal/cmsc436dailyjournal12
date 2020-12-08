package com.example.dailyjournal

/* Interface to specify all actions for Items. */

interface OnItemClickListener {
    fun onItemDelete(data : ListItem)
    fun onItemEdit(data : ListItem)
    fun onItemClick(data : ListItem)
}