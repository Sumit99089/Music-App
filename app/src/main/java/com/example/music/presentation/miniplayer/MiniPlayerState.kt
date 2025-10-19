package com.example.music.presentation.miniplayer

import com.example.music.domain.models.SongModel

data class MiniPlayerState(
    val nowPlaying: SongModel? = null,
    val isPlaying: Boolean = false
)
