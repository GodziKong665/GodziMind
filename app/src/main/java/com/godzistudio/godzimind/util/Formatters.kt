package com.godzistudio.godzimind.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

/** Convierte segundos a formato mm:ss (p. ej. 65 -> "01:05"). */
fun formatClock(totalSeconds: Int): String {
    val safe = totalSeconds.coerceAtLeast(0)
    return String.format(Locale.US, "%02d:%02d", safe / 60, safe % 60)
}

/**
 * Etiqueta amable para la fecha de creación de una tarea:
 * "Hoy · 14:32", "Ayer" o "12 nov".
 */
fun formatTaskDate(createdAt: Long, now: Long = System.currentTimeMillis()): String {
    val zone = ZoneId.systemDefault()
    val date = Instant.ofEpochMilli(createdAt).atZone(zone).toLocalDate()
    val today = Instant.ofEpochMilli(now).atZone(zone).toLocalDate()
    return when (date) {
        today -> "Hoy · " + Instant.ofEpochMilli(createdAt)
            .atZone(zone)
            .format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()))

        today.minusDays(1) -> "Ayer"
        else -> date.format(
            DateTimeFormatter.ofPattern("d MMM", Locale.forLanguageTag("es"))
        )
    }
}

/** Recorta un texto largo en un punto de frase razonable. */
fun shortenText(text: String, maxChars: Int = 320): String {
    val clean = text.replace(Regex("\\s+"), " ").trim()
    if (clean.length <= maxChars) return clean
    val cut = clean.take(maxChars)
    val lastStop = cut.lastIndexOfAny(charArrayOf('.', '!', '?'))
    return if (lastStop >= maxChars / 2) {
        cut.take(lastStop + 1)
    } else {
        cut.trimEnd() + "…"
    }
}

/** Utilidad para pruebas y para mostrar la fecha de hoy en encabezados. */
fun todayLabel(): String = LocalDate.now().format(
    DateTimeFormatter.ofPattern("EEEE d 'de' MMMM", Locale.forLanguageTag("es"))
)
