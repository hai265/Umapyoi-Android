package com.example.uma.ui.screens.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

@Composable
fun PlayAudioButton(
    audioUrl: String
) {
    val context = LocalContext.current

    val player = remember(audioUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(audioUrl))
            prepare()
        }
    }

    var isPlaying by remember { mutableStateOf(false) }
    var playbackState by remember { mutableIntStateOf(Player.STATE_IDLE) }

    // Listen to player state changes
    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }

            override fun onPlaybackStateChanged(state: Int) {
                playbackState = state
            }
        }

        player.addListener(listener)

        onDispose {
            player.removeListener(listener)
            player.release()
        }
    }

    IconButton(
        onClick = {
            when {
                playbackState == Player.STATE_ENDED -> {
                    player.seekTo(0)
                    player.play()
                }

                player.isPlaying -> {
                    player.pause()
                    player.seekTo(0)
                }

                else -> {
                    player.play()
                }
            }
        }
    ) {
        Icon(
            imageVector = if (isPlaying)
                Icons.Default.Pause
            else
                Icons.Default.PlayArrow,
            contentDescription = if (isPlaying) "Pause Audio" else "Play Audio"
        )
    }
}