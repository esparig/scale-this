package com.example.myapplication

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.PI
import kotlin.math.sin

class TonePlayer {

    companion object {
        // Frequencies for each scale note (in Hz)
        private val SCALE_FREQUENCIES = mapOf(
            "C" to 261.63,
            "Db" to 277.18,
            "D" to 293.66,
            "Eb" to 311.13,
            "E" to 329.63,
            "F" to 349.23,
            "F#" to 369.99,
            "G" to 392.00,
            "Ab" to 415.30,
            "A" to 440.00,
            "Bb" to 466.16,
            "B" to 493.88
        )

        private const val SAMPLE_RATE = 44100
        private const val DURATION_SECONDS = 1.0
    }

    suspend fun playTone(scale: String) = withContext(Dispatchers.IO) {
        val frequency = SCALE_FREQUENCIES[scale] ?: return@withContext

        val numSamples = (DURATION_SECONDS * SAMPLE_RATE).toInt()
        val samples = ShortArray(numSamples)

        // Generate sine wave
        for (i in samples.indices) {
            val angle = 2.0 * PI * i / (SAMPLE_RATE / frequency)
            samples[i] = (sin(angle) * Short.MAX_VALUE * 0.5).toInt().toShort()
        }

        // Create and play audio track
        val audioTrack = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(SAMPLE_RATE)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setBufferSizeInBytes(numSamples * 2)
            .setTransferMode(AudioTrack.MODE_STATIC)
            .build()

        audioTrack.write(samples, 0, numSamples)
        audioTrack.play()

        // Wait for playback to finish
        Thread.sleep((DURATION_SECONDS * 1000).toLong())

        audioTrack.stop()
        audioTrack.release()
    }
}

