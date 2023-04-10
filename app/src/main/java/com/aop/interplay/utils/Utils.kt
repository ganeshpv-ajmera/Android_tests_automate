package com.aop.interplay.utils

import java.util.*

class Utils {
    companion object{
         fun validateMobileNumber(input: String): Boolean {
            val regex = Regex("^\\d{10}$")
            return regex.matches(input)
        }

        fun userAge(dateInMillis:Long):Int {
            val calendar = Calendar.getInstance()
            val presentCalendar = Calendar.getInstance()
            calendar.timeInMillis = dateInMillis
            calendar.set(Calendar.HOUR_OF_DAY,0)
            calendar.set(Calendar.MINUTE,0)

            var diff: Int = presentCalendar.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)
            if (calendar.get(Calendar.MONTH) > presentCalendar.get(Calendar.MONTH) || calendar.get(Calendar.MONTH) == presentCalendar.get(
                    Calendar.MONTH
                ) && calendar.get(
                    Calendar.DATE
                ) > presentCalendar.get(Calendar.DATE)
            ) {
                diff--
            }
            return diff
        }
    }
}