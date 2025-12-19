package com.example.uma.audio

import android.media.AudioAttributes
import android.media.MediaPlayer
import javax.inject.Inject

class AudioPlayer @Inject constructor() {
    fun playAudio(url: String) {
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            prepare()
            start()
        }
    }
}