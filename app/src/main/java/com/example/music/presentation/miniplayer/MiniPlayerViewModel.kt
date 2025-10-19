package com.example.music.presentation.miniplayer

import androidx.lifecycle.ViewModel
import com.example.music.infrastructure.service.MusicServiceConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class MiniPlayerViewModel @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection
): ViewModel() {
    private val _state = MutableStateFlow(MiniPlayerState())
    val state = _state.asStateFlow()

    init {

    }

    fun observePlaybackState(){
        musicServiceConnection.playbackState
    }


}