package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScaleViewModel(application: Application) : AndroidViewModel(application) {

    private val scaleManager = ScaleManager(application.applicationContext)

    val shuffledScalesFlow: StateFlow<List<String>> = scaleManager.shuffledScalesFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val currentIndexFlow: StateFlow<Int> = scaleManager.currentIndexFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    val previousScaleFlow: StateFlow<String?> = scaleManager.previousScaleFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val completionCountFlow: StateFlow<Int> = scaleManager.completionCountFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    init {
        viewModelScope.launch {
            scaleManager.initializeShuffleIfNeeded()
        }
    }

    fun moveToNextScale(currentShuffledScales: List<String>, currentIndex: Int) {
        viewModelScope.launch {
            scaleManager.moveToNextScale(currentShuffledScales, currentIndex)
        }
    }
}

