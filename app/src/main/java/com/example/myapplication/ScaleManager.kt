package com.example.myapplication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "scale_preferences")

class ScaleManager(private val context: Context) {

    companion object {
        private val SHUFFLED_SCALES_KEY = stringPreferencesKey("shuffled_scales")
        private val CURRENT_INDEX_KEY = intPreferencesKey("current_index")
        private val PREVIOUS_SCALE_KEY = stringPreferencesKey("previous_scale")
        private val COMPLETION_COUNT_KEY = intPreferencesKey("completion_count")
        private const val SCALE_SEPARATOR = ","
    }

    private val allScales = listOf("C", "Db", "D", "Eb", "E", "F", "F#", "G", "Ab", "A", "Bb", "B")

    val shuffledScalesFlow: Flow<List<String>> = context.dataStore.data.map { preferences ->
        val scalesString = preferences[SHUFFLED_SCALES_KEY]
        if (scalesString.isNullOrEmpty()) {
            allScales.shuffled()
        } else {
            scalesString.split(SCALE_SEPARATOR)
        }
    }

    val currentIndexFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[CURRENT_INDEX_KEY] ?: 0
    }

    val previousScaleFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PREVIOUS_SCALE_KEY]
    }

    val completionCountFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[COMPLETION_COUNT_KEY] ?: 0
    }

    suspend fun initializeShuffleIfNeeded() {
        context.dataStore.edit { preferences ->
            val scalesString = preferences[SHUFFLED_SCALES_KEY]
            if (scalesString.isNullOrEmpty()) {
                // Initialize with shuffled scales on first launch
                val shuffled = allScales.shuffled()
                preferences[SHUFFLED_SCALES_KEY] = shuffled.joinToString(SCALE_SEPARATOR)
                preferences[CURRENT_INDEX_KEY] = 0
            }
        }
    }

    suspend fun moveToNextScale(currentShuffledScales: List<String>, currentIndex: Int) {
        val currentScale = currentShuffledScales[currentIndex]
        val nextIndex = currentIndex + 1

        context.dataStore.edit { preferences ->
            // Save the current scale as the previous scale
            preferences[PREVIOUS_SCALE_KEY] = currentScale

            if (nextIndex >= currentShuffledScales.size) {
                // Increment completion counter when finishing all 12 scales
                val currentCount = preferences[COMPLETION_COUNT_KEY] ?: 0
                preferences[COMPLETION_COUNT_KEY] = currentCount + 1

                // Reshuffle when all scales have been shown
                val newShuffledScales = allScales.shuffled()
                preferences[SHUFFLED_SCALES_KEY] = newShuffledScales.joinToString(SCALE_SEPARATOR)
                preferences[CURRENT_INDEX_KEY] = 0
            } else {
                preferences[CURRENT_INDEX_KEY] = nextIndex
            }
        }
    }
}

