package com.am.mute

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.os.Bundle

/**
 * @author Adhish
 */
class MuteActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxRingVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)
        val maxNotificationVolume =
            audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)
        audioManager.takeIf {
            it.getStreamVolume(AudioManager.STREAM_RING) > 0 || it.getStreamVolume(AudioManager.STREAM_NOTIFICATION) > 0
        }?.run {
            setStreamVolume(AudioManager.STREAM_RING, 0, 0)
            setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0)
        } ?: run {
            audioManager.setStreamVolume(AudioManager.STREAM_RING, maxRingVolume, 0)
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, maxNotificationVolume, 0)
        }
        finishAndRemoveTask()
    }
}
