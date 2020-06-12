package com.ddd.airplane.common.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun convertLongToYearDayTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm")
        return format.format(date)
    }
}