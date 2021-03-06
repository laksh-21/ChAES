package com.example.chaes.utilities

import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {
    companion object {
        fun convertDateToTime(date: Date): String {
            val dateFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
            return dateFormatter.format(date)
        }
    }
}