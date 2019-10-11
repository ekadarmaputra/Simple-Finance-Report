package com.example.simplefinancereport.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.convertForCalendar(): Date {
    //    val calendarDate = Calendar.getInstance()
//    calendarDate.time = convertedDate

    return SimpleDateFormat("MM/dd/yyyy").parse(this)
}