package com.example.music.presentation.miniplayer

import android.media.MediaMetadata
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.example.music.domain.models.SongModel
import com.example.music.infrastructure.service.MusicServiceConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MiniPlayerViewModel @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection
): ViewModel() {
    private val _state = MutableStateFlow(MiniPlayerState())
    val state = _state.asStateFlow()

    init {
        observePlayerState()
    }

    private fun observePlayerState() {
        // Get the two flows
        val nowPlayingFlow = musicServiceConnection.nowPlaying
        val isPlayingFlow = musicServiceConnection.isPlaying

        // Combine them
        nowPlayingFlow.combine(isPlayingFlow) { mediaItem, isPlaying ->
            // This block runs when *either* nowPlaying OR isPlaying emits

            // 1. Handle your "broken song" scenario
            if (mediaItem == null) {
                // If there is no song, we MUST be in a "not playing" state.
                // This fixes the contradiction.
                MiniPlayerState(nowPlaying = null, isPlaying = false)
            }
            // 2. Handle the normal state
            else {
                val songModel = mediaItemToSongModel(mediaItem)
                // We can trust the isPlaying value because we know a song is loaded.
                // This fixes the "pause" problem.
                MiniPlayerState(nowPlaying = songModel, isPlaying = isPlaying)
            }
        }.onEach { newState ->
            // Update the state with our new, consistent MiniPlayerState
            _state.value = newState
        }.launchIn(viewModelScope)
    }

    // Helper function to convert the MediaItem
    private fun mediaItemToSongModel(mediaItem: MediaItem): SongModel {
        val metadata = mediaItem.mediaMetadata
        return SongModel(
            id = mediaItem.mediaId.toLongOrNull() ?: 0L,
            title = metadata.title?.toString() ?: "Unknown Title",
            artist = metadata.artist?.toString() ?: "Unknown Artist",
            album = metadata.albumTitle?.toString() ?: "Unknown Album",
            duration = metadata.durationMs?.toLong() ?: 3L,
            contentUri = mediaItem.requestMetadata.mediaUri ?: Uri.EMPTY
        )
    }

    fun onEvent(event: MiniPlayerEvent){
        when(event){
            is MiniPlayerEvent.OnMiniPlayerTapped -> {

            }
            is MiniPlayerEvent.OnPlayPauseClicked -> {
                musicServiceConnection.togglePlayPause()
            }
        }
    }
}


/*
onEach does NOT terminate the flow.
It's an intermediate operator.
It just "plans" to do something for each item.
It returns a new Flow with that plan attached, but it doesn't actually run anything.

collect DOES terminate the flow.
It's a terminal operator.
This is the "Go" button that actually starts the flow,
begins listening for items, and suspends the coroutine to wait for them.
*/