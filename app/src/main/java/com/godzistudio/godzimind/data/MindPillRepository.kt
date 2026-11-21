package com.godzistudio.godzimind.data

import com.godzistudio.godzimind.util.shortenText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/** Contenido breve sobre un concepto de mente y bienestar. */
data class MindPill(
    val title: String,
    val topic: String,
    val extract: String,
    val url: String
)

/**
 * Datos de la "Píldora de mente".
 *
 * API elegida: REST de Wikipedia en español (api/rest_v1/page/summary). Encaja
 * con GodziMind porque ofrece microlecturas reales, en español y sin API key
 * sobre los temas que trabaja la app (atención plena, meditación, sueño...).
 * Si no hay red, se usa una selección local con el mismo formato.
 */
object MindPillRepository {

    private const val ENDPOINT = "https://es.wikipedia.org/api/rest_v1/page/summary/"

    private val topics = listOf(
        "Atención plena",
        "Meditación",
        "Respiración diafragmática",
        "Estrés",
        "Sueño",
        "Gratitud",
        "Técnica Pomodoro"
    )

    private val fallbackPills = listOf(
        MindPill(
            title = "Atención plena",
            topic = "Atención plena",
            extract = "La atención plena es la capacidad de estar presente en el aquí y ahora, " +
                "sin juzgar lo que aparece. Un minuto de respiración consciente ya es entrenamiento.",
            url = "https://es.wikipedia.org/wiki/Atenci%C3%B3n_plena"
        ),
        MindPill(
            title = "Meditación",
            topic = "Meditación",
            extract = "Meditar no es dejar la mente en blanco: es notar lo que pasa y volver, " +
                "con amabilidad, a un punto de anclaje como la respiración.",
            url = "https://es.wikipedia.org/wiki/Meditaci%C3%B3n"
        ),
        MindPill(
            title = "Respiración diafragmática",
            topic = "Respiración diafragmática",
            extract = "Respirar con el diafragma activa el sistema parasimpático y ayuda a bajar " +
                "el ritmo cardíaco. Coloca una mano en el abdomen y observa cómo se infla.",
            url = "https://es.wikipedia.org/wiki/Respiraci%C3%B3n_diafragm%C3%A1tica"
        ),
        MindPill(
            title = "Estrés",
            topic = "Estrés",
            extract = "El estrés es una respuesta adaptativa; se vuelve un problema cuando no " +
                "descansa. Una pausa corta y consciente le dice al cuerpo que puede salir del modo alerta.",
            url = "https://es.wikipedia.org/wiki/Estr%C3%A9s"
        ),
        MindPill(
            title = "Sueño",
            topic = "Sueño",
            extract = "Una rutina tranquila antes de dormir —luz baja, pantallas lejos y respiración " +
                "lenta— ayuda a que el cerebro cambie de marcha.",
            url = "https://es.wikipedia.org/wiki/Sue%C3%B1o"
        ),
        MindPill(
            title = "Gratitud",
            topic = "Gratitud",
            extract = "Anotar tres cosas buenas del día entrena la atención hacia lo que sí funciona. " +
                "No se trata de ignorar lo difícil, sino de no verlo todo con el mismo filtro.",
            url = "https://es.wikipedia.org/wiki/Gratitud"
        ),
        MindPill(
            title = "Técnica Pomodoro",
            topic = "Técnica Pomodoro",
            extract = "La técnica Pomodoro alterna bloques de enfoque y descansos. El cronómetro de " +
                "GodziMind sigue esa idea: un tiempo elegido, una sola tarea.",
            url = "https://es.wikipedia.org/wiki/T%C3%A9cnica_Pomodoro"
        )
    )

    val topicCount: Int get() = topics.size

    suspend fun fetch(index: Int): MindPill? = withContext(Dispatchers.IO) {
        val topic = topics[index % topics.size]
        var connection: HttpURLConnection? = null
        try {
            val encoded = URLEncoder.encode(topic.replace(" ", "_"), "UTF-8")
            connection = (URL(ENDPOINT + encoded).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 6_000
                readTimeout = 6_000
                setRequestProperty("Accept", "application/json")
                setRequestProperty("User-Agent", "GodziMind/1.0 (Android; app educativa)")
            }
            if (connection.responseCode !in 200..299) return@withContext null

            val body = connection.inputStream.bufferedReader().use { it.readText() }
            val json = JSONObject(body)
            val extract = json.optString("extract").takeIf { it.isNotBlank() }
                ?: return@withContext null
            val title = json.optString("title").ifBlank { topic }
            val url = json.optJSONObject("content_urls")
                ?.optJSONObject("desktop")
                ?.optString("page")
                ?.takeIf { it.isNotBlank() }
                ?: wikiUrl(topic)

            MindPill(
                title = title,
                topic = topic,
                extract = shortenText(extract),
                url = url
            )
        } catch (_: Exception) {
            null
        } finally {
            connection?.disconnect()
        }
    }

    fun fallback(index: Int): MindPill = fallbackPills[index % fallbackPills.size]

    private fun wikiUrl(topic: String): String =
        "https://es.wikipedia.org/wiki/" + URLEncoder.encode(topic.replace(" ", "_"), "UTF-8")
}
