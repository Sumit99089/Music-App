package com.example.music.infrastructure.service

import android.content.Intent
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MusicService: MediaSessionService() {

    @Inject
    lateinit var exoPlayer: ExoPlayer

    private var mediaSession: MediaSession? = null
    //MediaSession
    //Exposes playback state, metadata, and transport controls.
    //Acts like the server or authority for playback.
    //Wraps ExoPlayer so it can be controlled by other processes/apps.

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSession.Builder(this, exoPlayer).build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        mediaSession?.player?.let {player->
            if(!player.playWhenReady||player.mediaItemCount==0){
                stopSelf()
            }
        }
    }

    override fun onDestroy() {
        mediaSession?.run{
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }


}