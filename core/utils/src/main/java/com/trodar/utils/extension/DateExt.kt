package com.trodar.utils.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun Date.compareTo(yearMonth: String): Boolean {
    val formatter = SimpleDateFormat("yyyyMM")

    return formatter.format(this) == yearMonth
}

//fun Date.compareTo(date: Date): Boolean {
//    val formatter = SimpleDateFormat("yyyyMM")
//
//    return formatter.format(this) == formatter.format(date)
//}