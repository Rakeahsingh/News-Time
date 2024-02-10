package com.rkcoding.newstime.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun dateFormatter(inputDateTime: String?): String {
    val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val outputFormatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.LONG)
        .withLocale(Locale.getDefault())
    val dateString = try {
        val dateTime = OffsetDateTime.parse(inputDateTime, inputFormatter)
        dateTime.format(outputFormatter)
    } catch (e: Exception) {
        ""
    }
    return dateString
}