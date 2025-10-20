package com.example.music.infrastructure.service

import android.app.PendingIntent
import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.music.MainActivity
import com.example.music.infrastructure.notification.NotificationConstants
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

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        // 1. Create the PendingIntent to launch the UI
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        // 2. Build the MediaSession
        mediaSession = MediaSession.Builder(this, exoPlayer)
            .setSessionActivity(pendingIntent) // Tell the session what to open on tap
            .build()

        // 3. Create the Notification Provider
        val notificationProvider = DefaultMediaNotificationProvider.Builder(this)
            .setChannelId(NotificationConstants.NOTIFICATION_CHANNEL_ID)
            .build()

        // 4. Set the provider on the service
        setMediaNotificationProvider(notificationProvider)
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