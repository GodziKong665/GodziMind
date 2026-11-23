package com.godzistudio.godzimind.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.godzistudio.godzimind.domain.model.StudyProfile

private val studyTimeOptions = listOf(30, 60, 90, 120, 180, 240)
private val academicLevels = listOf(
    "Secundaria",
    "Bachillerato",
    "Licenciatura",
    "Maestría",
    "Doctorado"
)

fun studyTimeLabel(minutes: Int): String = when {
    minutes < 60 -> "$minutes min"
    minutes % 60 == 0 -> "${minutes / 60} h"
    else -> "${minutes / 60} h ${minutes % 60} min"
}

/**
 * Perfil de estudio y gustos. Se usa al iniciar la app (sin perfil) y también
 * desde el botón "Editar perfil" de la card de Inicio.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StudyProfileDialog(
    initialProfile: StudyProfile? = null,
    onDismiss: (() -> Unit)? = null,
    onSave: (StudyProfile) -> Unit
) {
    var selectedMinutes by rememberSaveable {
        mutableIntStateOf(initialProfile?.studyMinutesPerDay ?: 60)
    }
    var selectedLevel by rememberSaveable {
        mutableStateOf(initialProfile?.academicLevel ?: "")
    }
    var ageText by rememberSaveable {
        mutableStateOf(initialProfile?.age?.takeIf { it > 0 }?.toString() ?: "")
    }
    var occupation by rememberSaveable {
        mutableStateOf(initialProfile?.occupation ?: "")
    }
    var hobbies by rememberSaveable {
        mutableStateOf(initialProfile?.hobbies ?: "")
    }
    var musicGenre by rememberSaveable {
        mutableStateOf(initialProfile?.musicGenre ?: "")
    }

    AlertDialog(
        onDismissRequest = { onDismiss?.invoke() },
        properties = DialogProperties(
            dismissOnBackPress = onDismiss != null,
            dismissOnClickOutside = onDismiss != null
        ),
        title = {
            Text(if (initialProfile == null) "Tu perfil de estudio" else "Editar mi perfil")
        },
        text = {
            Column(
                modifier = Modifier
                    .heightIn(max = 470.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "¿Cuánto tiempo estudias al día?",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    studyTimeOptions.forEach { minutes ->
                        FilterChip(
                            selected = selectedMinutes == minutes,
                            onClick = { selectedMinutes = minutes },
                            label = { Text(studyTimeLabel(minutes)) },
                            shape = RoundedCornerShape(50)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Nivel académico",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    academicLevels.forEach { level ->
                        FilterChip(
                            selected = selectedLevel == level,
                            onClick = { selectedLevel = level },
                            label = { Text(level) },
                            shape = RoundedCornerShape(50)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = ageText,
                    onValueChange = { value ->
                        if (value.length <= 3 && value.all { it.isDigit() }) {
                            ageText = value
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Edad") },
                    placeholder = { Text("Ej. 20") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = occupation,
                    onValueChange = { occupation = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Ocupación") },
                    placeholder = { Text("Estudiante, trabajo, etc.") },
                    singleLine = true
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = hobbies,
                    onValueChange = { hobbies = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Hobbies") },
                    placeholder = { Text("Leer, dibujar, videojuegos…") },
                    minLines = 2
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = musicGenre,
                    onValueChange = { musicGenre = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Tipo de música") },
                    placeholder = { Text("Rock, pop, reggaetón…") },
                    singleLine = true
                )

                Spacer(Modifier.height(14.dp))
                Text(
                    text = "Se guarda en Room, en la tabla study_profiles.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(
                        StudyProfile(
                            id = initialProfile?.id ?: 1,
                            studyMinutesPerDay = selectedMinutes,
                            academicLevel = selectedLevel,
                            age = ageText.toIntOrNull() ?: 0,
                            occupation = occupation.trim(),
                            hobbies = hobbies.trim(),
                            musicGenre = musicGenre.trim()
                        )
                    )
                },
                enabled = selectedLevel.isNotBlank()
            ) {
                Text("Guardar")
            }
        },
        dismissButton = if (onDismiss != null) {
            {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        } else {
            null
        }
    )
}
