package com.am.mute

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    fun updateLastVolumeStream(
        sharedPreferences: SharedPreferences,
        currentAudioValue: Int,
        currentNotificationValue: Int
    ) = CoroutineScope(Dispatchers.Default).launch{
        sharedPreferences.edit().apply {
            putInt(CURRENT_AUDIO_VOLUME, currentAudioValue)
            putInt(CURRENT_NOTIFICATION_VOLUME, currentNotificationValue)
        }.apply()
    }


    fun getLastVolumeStream(sharedPreferences: SharedPreferences): Pair<Int, Int> = Pair(
        sharedPreferences.getInt(
            CURRENT_AUDIO_VOLUME, 0
        ), sharedPreferences.getInt(CURRENT_NOTIFICATION_VOLUME, 0)
    )

    private companion object {
        const val CURRENT_AUDIO_VOLUME = "current_audio_volume"
        const val CURRENT_NOTIFICATION_VOLUME = "current_notification_volume"
    }
}
