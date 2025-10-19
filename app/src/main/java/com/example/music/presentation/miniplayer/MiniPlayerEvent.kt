package com.example.music.presentation.miniplayer

sealed class MiniPlayerEvent {
    object OnPlayPauseClicked : MiniPlayerEvent()
    object OnMiniPlayerTapped : MiniPlayerEvent()
}