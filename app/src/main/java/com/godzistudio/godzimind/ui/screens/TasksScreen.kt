package com.godzistudio.godzimind.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.godzistudio.godzimind.domain.model.Task
import com.godzistudio.godzimind.ui.components.EmojiBadge
import com.godzistudio.godzimind.ui.components.GodziCard
import com.godzistudio.godzimind.ui.components.Pill
import com.godzistudio.godzimind.ui.theme.HeroGradient
import com.godzistudio.godzimind.ui.theme.OnHero
import com.godzistudio.godzimind.util.formatTaskDate

private enum class TaskFilter(val label: String) {
    All("Todas"),
    Pending("Pendientes"),
    Done("Hechas")
}

/**
 * Pantalla de pendientes: cabecera con progreso, alta de tareas, filtros y
 * una card por tarea. Todo se guarda localmente en el dispositivo.
 */
@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val pending = tasks.count { !it.done }
    val completed = tasks.size - pending
    val progress by animateFloatAsState(
        targetValue = if (tasks.isEmpty()) 0f else completed.toFloat() / tasks.size.toFloat(),
        animationSpec = tween(durationMillis = 450),
        label = "tasksProgress"
    )

    var input by rememberSaveable { mutableStateOf("") }
    var filterName by rememberSaveable { mutableStateOf(TaskFilter.All.name) }
    val filter = TaskFilter.valueOf(filterName)
    val focusManager = LocalFocusManager.current

    val visibleTasks = tasks
        .filter { task ->
            when (filter) {
                TaskFilter.All -> true
                TaskFilter.Pending -> !task.done
                TaskFilter.Done -> task.done
            }
        }

    fun addTask() {
        viewModel.addTask(input)
        input = ""
        focusManager.clearFocus()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            TasksHeaderCard(
                pending = pending,
                completed = completed,
                total = tasks.size,
                progress = progress,
                onClearCompleted = viewModel::clearCompleted
            )
        }

        item {
            AddTaskCard(
                value = input,
                onValueChange = { if (it.length <= 120) input = it },
                onAdd = ::addTask
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TaskFilter.entries.forEach { option ->
                    FilterChip(
                        selected = filter == option,
                        onClick = { filterName = option.name },
                        label = {
                            val count = when (option) {
                                TaskFilter.All -> tasks.size
                                TaskFilter.Pending -> pending
                                TaskFilter.Done -> completed
                            }
                            Text("${option.label} · $count")
                        },
                        shape = RoundedCornerShape(50),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
                            selectedLabelColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }

        if (visibleTasks.isEmpty()) {
            item {
                EmptyTasksCard(filter = filter)
            }
        } else {
            items(visibleTasks, key = { it.id }) { task ->
                TaskCard(
                    task = task,
                    onToggle = { viewModel.toggleTask(task) },
                    onDelete = { viewModel.deleteTask(task) }
                )
            }
        }

        if (tasks.isNotEmpty()) {
            item {
                Text(
                    text = "Toca una tarea para marcarla como hecha.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun TasksHeaderCard(
    pending: Int,
    completed: Int,
    total: Int,
    progress: Float,
    onClearCompleted: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.linearGradient(HeroGradient))
                .padding(22.dp)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                drawCircle(
                    color = OnHero.copy(alpha = 0.07f),
                    radius = size.width * 0.42f,
                    center = Offset(size.width * 0.95f, size.height * 0.0f)
                )
                drawCircle(
                    color = Color(0xFF5EEAD4).copy(alpha = 0.5f),
                    radius = 3.dp.toPx(),
                    center = Offset(size.width * 0.1f, size.height * 0.2f)
                )
            }

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    EmojiBadge(
                        emoji = "✅",
                        background = OnHero.copy(alpha = 0.14f),
                        size = 50.dp,
                        fontSize = 25.sp
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "Pendientes",
                            style = MaterialTheme.typography.headlineMedium,
                            color = OnHero
                        )
                        Text(
                            text = if (total == 0) {
                                "Tu mente, un poco más ligera."
                            } else {
                                "$pending por hacer · $completed ${
                                    if (completed == 1) "completada" else "completadas"
                                }"
                            },
                            style = MaterialTheme.typography.labelMedium,
                            color = OnHero.copy(alpha = 0.75f)
                        )
                    }
                    if (pending == 0 && total > 0) {
                        Pill(text = "Todo listo", color = OnHero.copy(alpha = 0.92f))
                    }
                }

                Spacer(Modifier.height(18.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(50)),
                    color = OnHero,
                    trackColor = OnHero.copy(alpha = 0.22f),
                    drawStopIndicator = {}
                )
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = when {
                            total == 0 -> "Empieza con una sola cosa."
                            pending == 0 -> "Todo listo por hoy. Disfruta la calma."
                            else -> "Una cosa a la vez también avanza."
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = OnHero.copy(alpha = 0.82f),
                        modifier = Modifier.weight(1f)
                    )
                    if (completed > 0) {
                        TextButton(
                            onClick = onClearCompleted,
                            contentPadding = PaddingValues(horizontal = 4.dp),
                            colors = ButtonDefaults.textButtonColors(contentColor = OnHero)
                        ) {
                            Text("Limpiar hechas", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AddTaskCard(
    value: String,
    onValueChange: (String) -> Unit,
    onAdd: () -> Unit
) {
    GodziCard(contentPadding = 18.dp) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            EmojiBadge(
                emoji = "➕",
                background = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                size = 40.dp,
                fontSize = 19.sp
            )
            Spacer(Modifier.width(10.dp))
            Column {
                Text(
                    text = "Nueva tarea",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Sácala de tu cabeza.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(14.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("¿Qué necesitas recordar?") },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onAdd() }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
                )
            )
            Spacer(Modifier.width(10.dp))
            Button(
                onClick = onAdd,
                enabled = value.isNotBlank(),
                modifier = Modifier.height(56.dp),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Text("Añadir")
            }
        }
    }
}

@Composable
private fun TaskCard(
    task: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.done) {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onToggle)
                .padding(start = 6.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.done,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                    uncheckedColor = MaterialTheme.colorScheme.outline
                )
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (task.done) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                    textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = formatTaskDate(task.createdAt),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onDelete) {
                Text(
                    text = "✕",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun EmptyTasksCard(filter: TaskFilter) {
    GodziCard(containerColor = MaterialTheme.colorScheme.surfaceVariant) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmojiBadge(
                emoji = "🌤️",
                background = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
                size = 58.dp,
                fontSize = 28.sp
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = when (filter) {
                    TaskFilter.All -> "Nada por aquí"
                    TaskFilter.Pending -> "No hay pendientes"
                    TaskFilter.Done -> "Aún no has completado nada"
                },
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = when (filter) {
                    TaskFilter.All -> "Agrega tu primera tarea y libera espacio mental."
                    TaskFilter.Pending -> "Todo lo que anotaste ya está hecho. Disfruta el momento."
                    TaskFilter.Done -> "Marca una tarea como hecha y aparecerá aquí."
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}
