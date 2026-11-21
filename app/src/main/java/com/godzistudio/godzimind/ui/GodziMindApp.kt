package com.godzistudio.godzimind.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.godzistudio.godzimind.ui.components.GodziBackground
import com.godzistudio.godzimind.ui.screens.HomeScreen
import com.godzistudio.godzimind.ui.screens.StudyProfileDialog
import com.godzistudio.godzimind.ui.screens.StudyProfileUiState
import com.godzistudio.godzimind.ui.screens.StudyProfileViewModel
import com.godzistudio.godzimind.ui.screens.TasksScreen

private enum class Destination(val label: String, val emoji: String) {
    Home("Inicio", "🧘"),
    Tasks("Pendientes", "✅")
}

@Composable
fun GodziMindApp() {
    val studyProfileViewModel: StudyProfileViewModel = hiltViewModel()
    val studyProfileState by studyProfileViewModel.state.collectAsStateWithLifecycle()

    if (studyProfileState is StudyProfileUiState.Empty) {
        StudyProfileDialog(onSave = studyProfileViewModel::guardar)
    }

    var destination by rememberSaveable { mutableStateOf(Destination.Home) }

    GodziBackground {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                Surface(
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.97f),
                    shadowElevation = 12.dp
                ) {
                    NavigationBar(
                        containerColor = Color.Transparent,
                        tonalElevation = 0.dp
                    ) {
                        Destination.entries.forEach { item ->
                            val selected = destination == item
                            NavigationBarItem(
                                selected = selected,
                                onClick = { destination = item },
                                icon = {
                                    Text(text = item.emoji, fontSize = 20.sp)
                                },
                                label = {
                                    Text(
                                        text = item.label,
                                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (destination) {
                    Destination.Home -> HomeScreen()
                    Destination.Tasks -> TasksScreen()
                }
            }
        }
    }
}
