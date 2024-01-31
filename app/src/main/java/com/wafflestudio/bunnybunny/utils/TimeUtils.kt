package com.wafflestudio.bunnybunny.utils

import java.util.concurrent.TimeUnit

fun convertEpochMillisToFormattedTime(millis: Long): String {
    val currentTimeMillis = System.currentTimeMillis()
    val diffMillis = currentTimeMillis - millis
    if (diffMillis < 0) {
        return ""
    }
    val seconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
    val days = TimeUnit.MILLISECONDS.toDays(diffMillis)
    return when {
        minutes < 1 -> "${seconds}초 전"
        hours < 1 -> "${minutes}분 전"
        days < 1 -> "${hours}시간 전"
        else -> "${days}일 전"
    }
}

fun formatProductTime(createMillis: Long, refreshMillis: Long): String {
    return if (createMillis >= refreshMillis) {
        convertEpochMillisToFormattedTime(createMillis)
    } else {
        "끌올 ${convertEpochMillisToFormattedTime(refreshMillis)}"
    }
}