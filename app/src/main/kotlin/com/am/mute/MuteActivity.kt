package com.am.mute

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

/**
 * @author Adhish
 */
class MuteActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val currentRingVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING)
        val currentNotificationVolume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION)
        audioManager.apply {
            if (currentRingVolume > 0) {
                viewModel.updateLastVolumeStream(sharedPreferences, currentRingVolume, currentNotificationVolume)
                setStreamVolume(AudioManager.STREAM_RING, 0, 0)
                setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0)
            } else {
                viewModel.getLastVolumeStream(sharedPreferences).apply {
                    setStreamVolume(AudioManager.STREAM_RING, first, 0)
                    setStreamVolume(AudioManager.STREAM_NOTIFICATION, second, 0)
                }
            }
        }
        finishAndRemoveTask()
    }
}
