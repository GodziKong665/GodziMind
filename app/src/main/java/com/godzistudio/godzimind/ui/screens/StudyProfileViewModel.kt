package com.godzistudio.godzimind.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.godzistudio.godzimind.domain.model.ProfileField
import com.godzistudio.godzimind.domain.model.StudyProfile
import com.godzistudio.godzimind.domain.repository.ProfileFieldRepository
import com.godzistudio.godzimind.domain.repository.StudyProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface StudyProfileUiState {
    data object Loading : StudyProfileUiState
    data object Empty : StudyProfileUiState
    data class Ready(val profile: StudyProfile) : StudyProfileUiState
}

@HiltViewModel
class StudyProfileViewModel @Inject constructor(
    private val repository: StudyProfileRepository,
    private val profileFieldRepository: ProfileFieldRepository
) : ViewModel() {

    val state: StateFlow<StudyProfileUiState> = repository.obtenerPerfil()
        .map { profile ->
            if (profile == null) {
                StudyProfileUiState.Empty
            } else {
                StudyProfileUiState.Ready(profile)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = StudyProfileUiState.Loading
        )

    val campos: StateFlow<List<ProfileField>> = profileFieldRepository.obtenerCampos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun guardar(profile: StudyProfile) {
        if (profile.academicLevel.isBlank()) return
        viewModelScope.launch {
            val updated = profile.copy(updatedAt = System.currentTimeMillis())
            if (state.value is StudyProfileUiState.Ready) {
                repository.actualizarPerfil(updated)
            } else {
                repository.guardarPerfil(updated)
            }
        }
    }

    fun guardarCampo(id: Long?, name: String, value: String) {
        val cleanName = name.trim()
        val cleanValue = value.trim()
        if (cleanName.isEmpty() || cleanValue.isEmpty()) return
        viewModelScope.launch {
            val existing = if (id != null) {
                campos.value.firstOrNull { it.id == id }
            } else {
                profileFieldRepository.buscarPorNombre(cleanName)
            }

            if (existing != null) {
                profileFieldRepository.actualizarCampo(
                    existing.copy(
                        name = cleanName,
                        value = cleanValue,
                        updatedAt = System.currentTimeMillis()
                    )
                )
            } else {
                profileFieldRepository.insertarCampo(
                    ProfileField(
                        name = cleanName,
                        value = cleanValue
                    )
                )
            }
        }
    }

    fun eliminarCampo(campo: ProfileField) {
        viewModelScope.launch {
            profileFieldRepository.eliminarCampo(campo)
        }
    }

    fun eliminarPerfil() {
        viewModelScope.launch {
            repository.eliminarPerfil()
            profileFieldRepository.eliminarTodos()
        }
    }
}
