package com.joo.miruni.presentation.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DateTimeFormatUtilTest {

    // formatDateWithRelative

    @Test
    fun `formatDateWithRelative should return yesterday for yesterday`() {
        val yesterday = LocalDate.now().minusDays(1)
        assertEquals("어제", DateTimeFormatUtil.formatDateWithRelative(yesterday))
    }

    @Test
    fun `formatDateWithRelative should return today for today`() {
        val today = LocalDate.now()
        assertEquals("오늘", DateTimeFormatUtil.formatDateWithRelative(today))
    }

    @Test
    fun `formatDateWithRelative should return tomorrow for tomorrow`() {
        val tomorrow = LocalDate.now().plusDays(1)
        assertEquals("내일", DateTimeFormatUtil.formatDateWithRelative(tomorrow))
    }

    @Test
    fun `formatDateWithRelative should return day after tomorrow`() {
        val dayAfterTomorrow = LocalDate.now().plusDays(2)
        assertEquals("내일 모레", DateTimeFormatUtil.formatDateWithRelative(dayAfterTomorrow))
    }

    @Test
    fun `formatDateWithRelative should format same year date`() {
        val today = LocalDate.now()
        val sameYearDate = today.plusDays(10)
        val expected = "${sameYearDate.monthValue}월 ${sameYearDate.dayOfMonth}일"
        assertEquals(expected, DateTimeFormatUtil.formatDateWithRelative(sameYearDate))
    }

    @Test
    fun `formatDateWithRelative should format different year date`() {
        val differentYearDate = LocalDate.of(LocalDate.now().year + 2, 3, 15)
        val expected = "3월 15일, ${differentYearDate.year}"
        assertEquals(expected, DateTimeFormatUtil.formatDateWithRelative(differentYearDate))
    }

    // formatDateTimeWithRelative

    @Test
    fun `formatDateTimeWithRelative should delegate to formatDateWithRelative`() {
        val todayDateTime = LocalDateTime.now()
        assertEquals("오늘", DateTimeFormatUtil.formatDateTimeWithRelative(todayDateTime))
    }

    @Test
    fun `formatDateTimeWithRelative should return tomorrow for tomorrow datetime`() {
        val tomorrowDateTime = LocalDateTime.now().plusDays(1)
        assertEquals("내일", DateTimeFormatUtil.formatDateTimeWithRelative(tomorrowDateTime))
    }

    // formatLocalTimeToString

    @Test
    fun `formatLocalTimeToString should format AM time`() {
        val time = LocalTime.of(9, 30)
        assertEquals("9:30 오전", DateTimeFormatUtil.formatLocalTimeToString(time))
    }

    @Test
    fun `formatLocalTimeToString should format PM time`() {
        val time = LocalTime.of(14, 15)
        assertEquals("2:15 오후", DateTimeFormatUtil.formatLocalTimeToString(time))
    }

    @Test
    fun `formatLocalTimeToString should format midnight as 12 AM`() {
        val time = LocalTime.of(0, 0)
        assertEquals("12:00 오전", DateTimeFormatUtil.formatLocalTimeToString(time))
    }

    @Test
    fun `formatLocalTimeToString should format noon as 12 PM`() {
        val time = LocalTime.of(12, 0)
        assertEquals("12:00 오후", DateTimeFormatUtil.formatLocalTimeToString(time))
    }

    @Test
    fun `formatLocalTimeToString should pad single digit minutes`() {
        val time = LocalTime.of(8, 5)
        assertEquals("8:05 오전", DateTimeFormatUtil.formatLocalTimeToString(time))
    }

    // convertLocalTimeToTime

    @Test
    fun `convertLocalTimeToTime should convert AM time`() {
        val time = LocalTime.of(9, 30)
        val result = DateTimeFormatUtil.convertLocalTimeToTime(time)
        assertEquals(9, result.hour)
        assertEquals(30, result.minute)
        assertEquals("오전", result.format)
    }

    @Test
    fun `convertLocalTimeToTime should convert PM time`() {
        val time = LocalTime.of(15, 45)
        val result = DateTimeFormatUtil.convertLocalTimeToTime(time)
        assertEquals(3, result.hour)
        assertEquals(45, result.minute)
        assertEquals("오후", result.format)
    }

    @Test
    fun `convertLocalTimeToTime should convert midnight`() {
        val time = LocalTime.of(0, 0)
        val result = DateTimeFormatUtil.convertLocalTimeToTime(time)
        assertEquals(12, result.hour)
        assertEquals(0, result.minute)
        assertEquals("오전", result.format)
    }

    @Test
    fun `convertLocalTimeToTime should convert noon`() {
        val time = LocalTime.of(12, 0)
        val result = DateTimeFormatUtil.convertLocalTimeToTime(time)
        assertEquals(12, result.hour)
        assertEquals(0, result.minute)
        assertEquals("오후", result.format)
    }

    // toLocalTime

    @Test
    fun `toLocalTime should convert AM time`() {
        val result = DateTimeFormatUtil.toLocalTime(9, 30, "오전")
        assertEquals(LocalTime.of(9, 30), result)
    }

    @Test
    fun `toLocalTime should convert PM time`() {
        val result = DateTimeFormatUtil.toLocalTime(3, 45, "오후")
        assertEquals(LocalTime.of(15, 45), result)
    }

    @Test
    fun `toLocalTime should convert 12 AM to midnight`() {
        val result = DateTimeFormatUtil.toLocalTime(12, 0, "오전")
        assertEquals(LocalTime.of(0, 0), result)
    }

    @Test
    fun `toLocalTime should convert 12 PM to noon`() {
        val result = DateTimeFormatUtil.toLocalTime(12, 0, "오후")
        assertEquals(LocalTime.of(12, 0), result)
    }

    @Test
    fun `toLocalTime should convert 12 30 AM`() {
        val result = DateTimeFormatUtil.toLocalTime(12, 30, "오전")
        assertEquals(LocalTime.of(0, 30), result)
    }

    @Test
    fun `toLocalTime should convert 12 30 PM`() {
        val result = DateTimeFormatUtil.toLocalTime(12, 30, "오후")
        assertEquals(LocalTime.of(12, 30), result)
    }

    // getCurrentTimeIn5MinIntervals

    @Test
    fun `getCurrentTimeIn5MinIntervals should return time with minute divisible by 5`() {
        val result = DateTimeFormatUtil.getCurrentTimeIn5MinIntervals()
        assertEquals(0, result.minute % 5)
    }

    @Test
    fun `getCurrentTimeIn5MinIntervals should return time at or after now`() {
        val before = LocalTime.now()
        val result = DateTimeFormatUtil.getCurrentTimeIn5MinIntervals()
        // Result should be >= now (rounded up), or wrapped around midnight
        // We allow a small window for the test to run
        assertTrue(
            result >= before.withSecond(0).withNano(0) ||
                    // Handles midnight wrap-around edge case
                    (before.hour == 23 && before.minute >= 56 && result.hour == 0)
        )
    }
}
