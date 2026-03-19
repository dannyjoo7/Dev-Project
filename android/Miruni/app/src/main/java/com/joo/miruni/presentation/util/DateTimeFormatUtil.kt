package com.joo.miruni.presentation.util

import com.joo.miruni.presentation.widget.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateTimeFormatUtil {

    /**
     * LocalDate를 "어제/오늘/내일/내일 모레/M월 d일" 형식으로 포맷
     */
    fun formatDateWithRelative(date: LocalDate): String {
        val today = LocalDate.now()
        return when {
            date.isEqual(today.minusDays(1)) -> "어제"
            date.isEqual(today) -> "오늘"
            date.isEqual(today.plusDays(1)) -> "내일"
            date.isEqual(today.plusDays(2)) -> "내일 모레"
            else -> {
                if (date.year == today.year) {
                    date.format(DateTimeFormatter.ofPattern("M월 d일"))
                } else {
                    date.format(DateTimeFormatter.ofPattern("M월 d일, yyyy"))
                }
            }
        }
    }

    /**
     * LocalDateTime을 "어제/오늘/내일/M월 d일" 형식으로 포맷 (HomeViewModel용)
     */
    fun formatDateTimeWithRelative(dateTime: LocalDateTime): String {
        return formatDateWithRelative(dateTime.toLocalDate())
    }

    /**
     * LocalTime을 "H:mm 오전/오후" 형식으로 포맷
     */
    fun formatLocalTimeToString(localTime: LocalTime): String {
        val hour = localTime.hour
        val minute = localTime.minute

        val format = if (hour < 12) "오전" else "오후"
        val adjustedHour = if (hour % 12 == 0) 12 else hour % 12

        return "${adjustedHour}:${minute.toString().padStart(2, '0')} $format"
    }

    /**
     * LocalTime을 Time(hour, minute, format) 객체로 변환
     */
    fun convertLocalTimeToTime(localTime: LocalTime): Time {
        val hour = localTime.hour
        val minute = localTime.minute

        val format = if (hour < 12) "오전" else "오후"
        val adjustedHour = if (hour % 12 == 0) 12 else hour % 12

        return Time(adjustedHour, minute, format)
    }

    /**
     * 시간 선택기에서 받은 시/분/포맷을 LocalTime으로 변환
     */
    fun toLocalTime(hour: Int, minute: Int, format: String): LocalTime {
        val adjustedHour = when {
            format == "오후" && hour != 12 -> hour + 12
            format == "오전" && hour == 12 -> 0
            else -> hour
        }
        return LocalTime.of(adjustedHour, minute)
    }

    /**
     * 현재 시간을 5분 단위로 올림 조정
     */
    fun getCurrentTimeIn5MinIntervals(): LocalTime {
        val now = LocalTime.now()
        val adjustedMinute = ((now.minute + 4) / 5) * 5

        val newHour = if (adjustedMinute >= 60) {
            (now.hour + 1) % 24
        } else {
            now.hour
        }

        val newMinute = adjustedMinute % 60
        return LocalTime.of(newHour, newMinute)
    }
}
