package com.example.simplefinancereport.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDate(time: Long): String {
    val format = "dd/MM/yyyy"
    val dateFormat = SimpleDateFormat(format, Locale("id"))
    return dateFormat.format(time)
}