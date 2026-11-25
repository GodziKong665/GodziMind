package com.godzistudio.godzimind.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FormattersTest {

    @Test
    fun clockFormatsMinutesAndSeconds() {
        assertEquals("00:00", formatClock(0))
        assertEquals("00:59", formatClock(59))
        assertEquals("01:05", formatClock(65))
        assertEquals("25:00", formatClock(25 * 60))
        assertEquals("60:00", formatClock(60 * 60))
    }

    @Test
    fun clockNeverShowsNegativeValues() {
        assertEquals("00:00", formatClock(-30))
    }

    @Test
    fun todayDateUsesFriendlyLabel() {
        val now = System.currentTimeMillis()
        assertTrue(formatTaskDate(now, now).startsWith("Hoy"))
    }

    @Test
    fun longTextIsShortened() {
        val text = "La atención plena es la capacidad de estar presente. ".repeat(12)
        val shortened = shortenText(text, maxChars = 120)
        assertTrue(shortened.length <= 121)
        assertTrue(shortened.endsWith(".") || shortened.endsWith("…"))
    }
}
