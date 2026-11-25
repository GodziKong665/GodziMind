package com.godzistudio.godzimind.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.godzistudio.godzimind.domain.model.FocusSession
import com.godzistudio.godzimind.domain.repository.FocusSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FocusSessionViewModel @Inject constructor(
    private val repository: FocusSessionRepository
) : ViewModel() {

    val sesiones: StateFlow<List<FocusSession>> = repository.obtenerSesiones()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun insertarSesion(minutes: Int) {
        if (minutes <= 0) return
        viewModelScope.launch {
            repository.insertarSesion(FocusSession(minutes = minutes))
        }
    }

    fun actualizarSesion(sesion: FocusSession) {
        viewModelScope.launch {
            repository.actualizarSesion(sesion)
        }
    }

    fun eliminarSesion(sesion: FocusSession) {
        viewModelScope.launch {
            repository.eliminarSesion(sesion)
        }
    }
}
