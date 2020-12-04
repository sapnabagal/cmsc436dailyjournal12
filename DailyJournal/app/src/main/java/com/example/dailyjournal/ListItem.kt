package com.example.dailyjournal

import java.time.LocalDate

interface ListItem {

    fun getListItemType(): Int
    fun getDate(): LocalDate

    companion object {
        const val TYPE_AUDIO = 1
        const val TYPE_VIDEO = 2
        const val TYPE_TEXT = 3
        const val TYPE_PIC = 4
    }

}