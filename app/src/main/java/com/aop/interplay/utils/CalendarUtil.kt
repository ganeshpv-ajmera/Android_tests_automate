package com.aop.interplay.utils

import java.text.SimpleDateFormat
import java.util.*

class CalendarUtil {
    companion object{
        fun formatDate(calendar: Calendar): String {
            val simpleDateFormat = SimpleDateFormat("MMMM d,yyyy", Locale.getDefault())
            return simpleDateFormat.format(calendar.time)
        }
    }
}