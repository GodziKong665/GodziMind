package com.godzistudio.godzimind.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.godzistudio.godzimind.data.MindPill
import com.godzistudio.godzimind.data.MindPillRepository
import com.godzistudio.godzimind.data.NetworkStatus
import com.godzistudio.godzimind.ui.components.CardHeading
import com.godzistudio.godzimind.ui.components.GodziCard
import com.godzistudio.godzimind.ui.components.Pill
import java.time.LocalDate

private sealed interface PillUiState {
    data object Loading : PillUiState
    data class Ready(val pill: MindPill, val fromNetwork: Boolean) : PillUiState
}

/**
 * Microlectura diaria obtenida de la API REST de Wikipedia en español.
 * Si no hay conexión (o la API falla) se muestra una píldora local.
 */
@Composable
fun MindPillCard() {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    var request by rememberSaveable { mutableIntStateOf(LocalDate.now().dayOfYear) }
    var state by remember { mutableStateOf<PillUiState>(PillUiState.Loading) }
    val loading = state is PillUiState.Loading

    LaunchedEffect(request) {
        state = PillUiState.Loading
        val online = NetworkStatus.isOnline(context)
        val remote = if (online) MindPillRepository.fetch(request) else null
        state = PillUiState.Ready(
            pill = remote ?: MindPillRepository.fallback(request),
            fromNetwork = remote != null
        )
    }

    GodziCard {
        CardHeading(
            emoji = "💡",
            title = "Píldora de mente",
            subtitle = "Un minuto de lectura para tu curiosidad.",
            trailing = {
                TextButton(
                    onClick = { request += 1 },
                    enabled = !loading,
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text("Otra")
                }
            }
        )

        Spacer(Modifier.height(14.dp))
        when (val current = state) {
            PillUiState.Loading -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "Buscando una idea…",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            is PillUiState.Ready -> {
                Pill(
                    text = current.pill.topic,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = current.pill.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = current.pill.extract,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            runCatching { uriHandler.openUri(current.pill.url) }
                        },
                        contentPadding = PaddingValues(horizontal = 2.dp)
                    ) {
                        Text("Leer en Wikipedia")
                    }
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = if (current.fromNetwork) "Fuente: Wikipedia ES" else "Contenido local",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
