package com.trodar.utils.feature

import android.annotation.SuppressLint
import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun getMonthByNumber(monthNum:Int):String {
    val c = Calendar.getInstance()
    val monthDate = SimpleDateFormat("LLLL")
    c[Calendar.MONTH] = monthNum-1
    return monthDate.format(c.time)
}

@SuppressLint("SimpleDateFormat")
fun getDate(date: Date, context: Context): String {
    val dateFormat = android.text.format.DateFormat.getDateFormat(context).format(date)
    val formatter = SimpleDateFormat("EEEE, HH:mm").format(date)

    return "$dateFormat,  $formatter"
}
