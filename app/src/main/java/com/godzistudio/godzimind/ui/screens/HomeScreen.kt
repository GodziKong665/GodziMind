package com.godzistudio.godzimind.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.godzistudio.godzimind.R
import com.godzistudio.godzimind.domain.model.ProfileField
import com.godzistudio.godzimind.ui.components.CardHeading
import com.godzistudio.godzimind.ui.components.EmojiBadge
import com.godzistudio.godzimind.ui.components.GodziCard
import com.godzistudio.godzimind.ui.components.InfoRow
import com.godzistudio.godzimind.ui.components.Pill
import com.godzistudio.godzimind.ui.theme.HeroGradient
import com.godzistudio.godzimind.ui.theme.OnHero
import com.godzistudio.godzimind.util.todayLabel
import java.util.Locale

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { HeroCard() }
        item { StudyProfileSummaryCard() }
        item { FocusTimerCard() }
        item { AboutCard() }
        item { MindPillCard() }
        item { HowItWorksCard() }
        item { BenefitsCard() }
        item { PrivacyCard() }
        item { GentleNote() }
    }
}

@Composable
private fun HeroCard() {
    Card(
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.linearGradient(HeroGradient))
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                drawCircle(
                    color = OnHero.copy(alpha = 0.07f),
                    radius = size.width * 0.46f,
                    center = Offset(size.width * 0.94f, -size.height * 0.05f)
                )
                drawCircle(
                    color = OnHero.copy(alpha = 0.05f),
                    radius = size.width * 0.34f,
                    center = Offset(size.width * 0.05f, size.height * 1.02f)
                )
                drawCircle(
                    color = Color(0xFF5EEAD4).copy(alpha = 0.55f),
                    radius = 3.dp.toPx(),
                    center = Offset(size.width * 0.82f, size.height * 0.22f)
                )
            }

            Column(modifier = Modifier.padding(24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.godzi_logo),
                        contentDescription = "Logo GodziMind",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(14.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "GodziMind",
                            style = MaterialTheme.typography.headlineMedium,
                            color = OnHero
                        )
                        Text(
                            text = "Calma + foco",
                            style = MaterialTheme.typography.labelMedium,
                            color = OnHero.copy(alpha = 0.72f)
                        )
                    }
                    Pill(text = "v1.0", color = OnHero.copy(alpha = 0.9f))
                }

                Spacer(Modifier.height(22.dp))
                Text(
                    text = "Entrena tu mente.\nOrdena tu día.",
                    style = MaterialTheme.typography.headlineLarge,
                    color = OnHero
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Un lugar para respirar, enfocarte y soltar todo lo que traes en la cabeza.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnHero.copy(alpha = 0.80f)
                )
                Spacer(Modifier.height(18.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    GlassTag("⏱️ Enfoque")
                    GlassTag("✅ Pendientes")
                    GlassTag("🌿 Calma")
                }
                Spacer(Modifier.height(18.dp))
                Text(
                    text = todayLabel().uppercase(Locale.forLanguageTag("es")),
                    style = MaterialTheme.typography.labelSmall,
                    color = OnHero.copy(alpha = 0.58f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun GlassTag(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(OnHero.copy(alpha = 0.12f))
            .border(1.dp, OnHero.copy(alpha = 0.18f), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = OnHero.copy(alpha = 0.92f)
        )
    }
}

@Composable
private fun StudyProfileSummaryCard() {
    val viewModel: StudyProfileViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val campos by viewModel.campos.collectAsStateWithLifecycle()
    var editing by rememberSaveable { mutableStateOf(false) }
    var addingField by rememberSaveable { mutableStateOf(false) }
    var editingField by remember { mutableStateOf<ProfileField?>(null) }
    var confirmDelete by rememberSaveable { mutableStateOf(false) }
    val profile = (state as? StudyProfileUiState.Ready)?.profile ?: return

    if (editing) {
        StudyProfileDialog(
            initialProfile = profile,
            onDismiss = { editing = false },
            onSave = { updated ->
                viewModel.guardar(updated)
                editing = false
            }
        )
    }

    if (addingField || editingField != null) {
        ProfileFieldDialog(
            initial = editingField,
            onDismiss = {
                addingField = false
                editingField = null
            },
            onSave = { name, value ->
                viewModel.guardarCampo(editingField?.id, name, value)
                addingField = false
                editingField = null
            }
        )
    }

    if (confirmDelete) {
        AlertDialog(
            onDismissRequest = { confirmDelete = false },
            title = { Text("¿Borrar perfil?") },
            text = {
                Text(
                    "Se borrarán tu perfil y tus campos personalizados. " +
                        "Podrás volver a crearlo."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.eliminarPerfil()
                        confirmDelete = false
                    }
                ) {
                    Text("Borrar")
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmDelete = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    GodziCard {
        CardHeading(
            emoji = "🧠",
            title = "Mi perfil",
            subtitle = "Tu ritmo de estudio y tus gustos."
        )
        Spacer(Modifier.height(14.dp))
        ProfileLine("Tiempo de estudio", studyTimeLabel(profile.studyMinutesPerDay))
        ProfileLine("Nivel académico", profile.academicLevel.ifBlank { "Sin dato" })
        ProfileLine("Edad", if (profile.age > 0) "${profile.age} años" else "Sin dato")
        ProfileLine("Ocupación", profile.occupation.ifBlank { "Sin dato" })
        ProfileLine("Hobbies", profile.hobbies.ifBlank { "Sin dato" })
        ProfileLine("Música", profile.musicGenre.ifBlank { "Sin dato" })

        HorizontalDivider(
            modifier = Modifier.padding(top = 12.dp, bottom = 10.dp),
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)
        )
        Text(
            text = "Campos personalizados",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        if (campos.isEmpty()) {
            Text(
                text = "Agrega otros gustos o datos que quieras guardar.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            campos.forEach { campo ->
                ProfileFieldRow(
                    field = campo,
                    onEdit = { editingField = campo },
                    onDelete = { viewModel.eliminarCampo(campo) }
                )
            }
        }
        TextButton(
            onClick = { addingField = true },
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            Text("+ Agregar campo")
        }
        TextButton(
            onClick = { editing = true },
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            Text("Editar perfil")
        }
        TextButton(
            onClick = { confirmDelete = true },
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            Text(
                text = "Borrar perfil",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun ProfileFieldRow(
    field: ProfileField,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = field.name,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = field.value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        IconButton(onClick = onEdit) {
            Text(
                text = "✎",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(onClick = onDelete) {
            Text(
                text = "✕",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ProfileLine(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            modifier = Modifier.width(128.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun AboutCard() {
    GodziCard {
        CardHeading(
            emoji = "🌿",
            title = "¿Qué hace GodziMind?",
            subtitle = "Dos rituales simples, una mente más clara."
        )
        Spacer(Modifier.height(14.dp))
        Text(
            text = "GodziMind une un cronómetro de respiración para volver al presente y una " +
                "lista de pendientes para liberar tu memoria. Menos ruido mental, más cosas hechas.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(18.dp))
        InfoRow(
            emoji = "⏳",
            title = "Enfoque a tu medida",
            body = "Tú eliges el tiempo, de 1 a 60 minutos, y el cronómetro sostiene el ritmo."
        )
        Spacer(Modifier.height(14.dp))
        InfoRow(
            emoji = "📝",
            title = "Mente sin ruido",
            body = "Anota lo que no quieres olvidar y sácalo de la cabeza por un rato.",
            tint = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.height(14.dp))
        InfoRow(
            emoji = "📈",
            title = "Progreso amable",
            body = "Sesiones cortas y tareas cumplidas. Sin metas imposibles ni culpa.",
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
private fun HowItWorksCard() {
    GodziCard {
        CardHeading(
            emoji = "🗺️",
            title = "Cómo se usa",
            subtitle = "En tres pasos, sin complicarte."
        )
        Spacer(Modifier.height(18.dp))
        StepRow(
            number = 1,
            title = "Elige tu momento",
            body = "Selecciona un tiempo en el cronómetro: 5 minutos para una pausa o 25 para enfocarte."
        )
        Spacer(Modifier.height(16.dp))
        StepRow(
            number = 2,
            title = "Respira con el reloj",
            body = "Inicia la cuenta atrás, suelta el teléfono y deja que la mente baje de revoluciones."
        )
        Spacer(Modifier.height(16.dp))
        StepRow(
            number = 3,
            title = "Vacía tu mente",
            body = "Pasa a Pendientes y escribe lo que necesitas recordar. Una cosa a la vez."
        )
    }
}

@Composable
private fun StepRow(number: Int, title: String, body: String) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.13f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun BenefitsCard() {
    GodziCard {
        CardHeading(
            emoji = "🌱",
            title = "Por qué funciona",
            subtitle = "Pequeños gestos, efectos reales."
        )
        Spacer(Modifier.height(16.dp))
        InfoRow(
            emoji = "🧠",
            title = "Menos carga mental",
            body = "Escribir un pendiente libera memoria de trabajo: dejas de repetirlo para no olvidarlo."
        )
        Spacer(Modifier.height(14.dp))
        InfoRow(
            emoji = "🎯",
            title = "Atención sostenida",
            body = "Un tiempo delimitado y visible entrena la concentración sin exigirte de más.",
            tint = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.height(14.dp))
        InfoRow(
            emoji = "💤",
            title = "Calma acumulativa",
            body = "Pocos minutos al día bajan el ritmo del cuerpo y preparan mejor el descanso.",
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
private fun PrivacyCard() {
    GodziCard(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            EmojiBadge(
                emoji = "🔒",
                background = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                size = 42.dp,
                fontSize = 20.sp
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = "Tus datos se quedan contigo",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Los pendientes viven solo en este teléfono. La única conexión externa es la " +
                        "API de Wikipedia para la píldora de mente.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun GentleNote() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "🌿", fontSize = 20.sp)
        Spacer(Modifier.height(6.dp))
        Text(
            text = "No necesitas hacerlo perfecto. Solo volver.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
