package com.godzistudio.godzimind.ui.screens

import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.godzistudio.godzimind.domain.model.FocusSession
import com.godzistudio.godzimind.domain.repository.FocusSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Estado del cronómetro fuera de la composición: así sigue contando si el
 * usuario cambia de pestaña, gira el teléfono o sale un momento de la app.
 */
@HiltViewModel
class FocusTimerViewModel @Inject constructor(
    private val focusSessionRepository: FocusSessionRepository
) : ViewModel() {

    var totalSeconds by mutableIntStateOf(DEFAULT_MINUTES * 60)
        private set

    var remainingSeconds by mutableIntStateOf(DEFAULT_MINUTES * 60)
        private set

    var isRunning by mutableStateOf(false)
        private set

    var isCompleted by mutableStateOf(false)
        private set

    private var job: Job? = null
    private var endAtMillis: Long = 0L

    /** Cambia la duración configurada (solo cuando está en pausa). */
    fun setMinutes(minutes: Int) {
        if (isRunning) return
        val safe = minutes.coerceIn(MIN_MINUTES, MAX_MINUTES)
        totalSeconds = safe * 60
        remainingSeconds = totalSeconds
        isCompleted = false
    }

    fun toggle() {
        if (isRunning) pause() else start()
    }

    fun reset() {
        job?.cancel()
        job = null
        isRunning = false
        isCompleted = false
        remainingSeconds = totalSeconds
    }

    private fun start() {
        if (remainingSeconds <= 0) {
            remainingSeconds = totalSeconds
            isCompleted = false
        }
        isRunning = true
        endAtMillis = SystemClock.elapsedRealtime() + remainingSeconds * 1_000L
        job?.cancel()
        job = viewModelScope.launch {
            while (isRunning) {
                val leftMillis = endAtMillis - SystemClock.elapsedRealtime()
                val leftSeconds = ((leftMillis + 999L) / 1_000L).toInt().coerceAtLeast(0)
                remainingSeconds = leftSeconds
                if (leftSeconds <= 0) {
                    isRunning = false
                    isCompleted = true
                    viewModelScope.launch {
                        focusSessionRepository.insertarSesion(
                            FocusSession(minutes = (totalSeconds / 60).coerceAtLeast(1))
                        )
                    }
                    break
                }
                delay(200L)
            }
        }
    }

    private fun pause() {
        isRunning = false
        job?.cancel()
        job = null
    }

    private companion object {
        const val DEFAULT_MINUTES = 25
        const val MIN_MINUTES = 1
        const val MAX_MINUTES = 60
    }
}
