package com.example.music.service

import android.content.ComponentName
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.music.domain.models.SongModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MusicServiceConnection @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    //SateFlows Exposed to ViewmModel and UI
    private val _isConnected: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isConnected:StateFlow<Boolean>  = _isConnected.asStateFlow()

    private val _playbackState: MutableStateFlow<Int> = MutableStateFlow(Player.STATE_IDLE)
    val playbackState:StateFlow<Int> = _playbackState.asStateFlow()

    private val _nowPlaying: MutableStateFlow<MediaItem?> = MutableStateFlow<MediaItem?>(null)
    val nowPlaying:StateFlow<MediaItem?> = _nowPlaying.asStateFlow()

    //Controller Exposed to ViewmModel and UI
    private var mediaController: MediaController? = null
    private var mediaItem: List<MediaItem> = emptyList()

    init{
        val sessionToken = SessionToken(context, ComponentName(context, MusicService::class.java))
        val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                mediaController = controllerFuture.get()
                mediaController?.addListener(PlayerEventListener())
                _isConnected.value = true
            },
            ContextCompat.getMainExecutor(context)
        )
        //MediaController is a remote control interface created from a SessionToken.
        //Think of it like a proxy or client library that knows how to talk to the MediaSession.
        //It gives your app (UI, ViewModel, etc.) a safe way to call play(), pause(), skipToNext(), etc., without directly touching ExoPlayer.
        //It also receives updates (playback state, metadata) from the MediaSession.
    }

    fun setMediaSongs(songs: List<SongModel>){
        mediaController?.setMediaItems(
            songs.map { song ->
                MediaItem.Builder().setMediaId(song.id.toString()).setUri(song.contentUri).build()
            }
        )
        mediaController?.prepare()
    }

    fun playSong(song: SongModel){
        val songIndex =  mediaItem.indexOfFirst {  it.mediaId == song.id.toString()}
        if(songIndex!=-1){
            mediaController?.seekTo(songIndex, 0)
            mediaController?.play()
        }
    }

    fun togglePlayPause(){
        if(mediaController?.isPlaying == true){
            mediaController?.pause()
        }
        else{
            mediaController?.play()
        }
    }

    fun skipToNext(){
        mediaController?.seekToNext()
    }

    fun skipToPrevious(){
        mediaController?.seekToPrevious()
    }

    fun release(){
        mediaController?.release()
        mediaController = null
        _isConnected.value = false
    }

    private inner class PlayerEventListener: Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            _playbackState.value = playbackState
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            _nowPlaying.value = mediaItem
        }

    }
}

/*
UI (MusicServiceConnection with MediaController)
      ↓
 MediaController (client-side)
      ↓ via SessionToken
 MediaSession (service-side)
      ↓
 ExoPlayer (actual player that plays audio)
 */