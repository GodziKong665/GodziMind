package com.godzistudio.godzimind.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.godzistudio.godzimind.ui.components.CardHeading
import com.godzistudio.godzimind.ui.components.GodziCard
import com.godzistudio.godzimind.util.formatClock
import kotlin.math.roundToInt

private val PresetMinutes = listOf(1, 5, 10, 15, 25, 45, 60)

/**
 * Card del cronómetro: el usuario elige entre 1 y 60 minutos con chips o con
 * el deslizador, y puede iniciar, pausar o reiniciar la cuenta atrás.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FocusTimerCard(viewModel: FocusTimerViewModel = hiltViewModel()) {
    val total = viewModel.totalSeconds
    val remaining = viewModel.remainingSeconds
    val running = viewModel.isRunning
    val completed = viewModel.isCompleted

    val targetProgress = when {
        completed -> 1f
        total <= 0 -> 0f
        else -> remaining.toFloat() / total.toFloat()
    }
    val progress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 350),
        label = "timerProgress"
    )

    val status = when {
        completed -> "Sesión completada"
        running -> "Respira profundo"
        remaining < total -> "En pausa"
        else -> "Listo cuando tú quieras"
    }

    GodziCard {
        CardHeading(
            emoji = "⏱️",
            title = "Cronómetro de enfoque",
            subtitle = "Tú eliges el tiempo. La app sostiene el ritmo."
        )

        Spacer(Modifier.height(18.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PresetMinutes.forEach { minutes ->
                FilterChip(
                    selected = total == minutes * 60,
                    onClick = { viewModel.setMinutes(minutes) },
                    enabled = !running,
                    label = { Text("$minutes min") },
                    shape = RoundedCornerShape(50),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
                        selectedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.size(218.dp),
                strokeWidth = 12.dp,
                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
                strokeCap = StrokeCap.Round,
                color = if (completed) {
                    MaterialTheme.colorScheme.tertiary
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = formatClock(remaining),
                    style = MaterialTheme.typography.displayLarge.copy(fontFamily = FontFamily.Monospace),
                    color = if (completed) {
                        MaterialTheme.colorScheme.tertiary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = status,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(14.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ajusta tu tiempo",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${total / 60} min",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Slider(
            value = total / 60f,
            onValueChange = { value -> viewModel.setMinutes(value.roundToInt()) },
            valueRange = 1f..60f,
            enabled = !running,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "1 min",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "60 min",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (running) {
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Pausa el cronómetro para cambiar el tiempo.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = viewModel::toggle,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = when {
                        running -> "Pausar"
                        remaining in 1 until total -> "Reanudar"
                        else -> "Iniciar"
                    }
                )
            }
            OutlinedButton(
                onClick = viewModel::reset,
                enabled = running || remaining != total || completed,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Reiniciar")
            }
        }

        if (completed) {
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.65f))
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "🌱", fontSize = 22.sp)
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        text = "Bien hecho",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text(
                        text = "Toma un vaso de agua y vuelve cuando lo necesites.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.85f)
                    )
                }
            }
        }
    }
}
