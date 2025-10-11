package com.example.music.presentation.library

import com.example.music.domain.models.SongModel

sealed class LibraryEvent {
    // Event for when the screen first loads
    object LoadMusic : LibraryEvent()

    // Event when a user taps a song (carries data)
    data class OnSongClicked(val song: SongModel) : LibraryEvent()

    // Event when a user changes the tab (carries data)
    data class OnTabSelected(val tab: Tab) : LibraryEvent()

    // Simple events with no extra data
    object OnSearchClicked : LibraryEvent()
    object OnPlayPauseClicked : LibraryEvent()
    object OnMiniPlayerTapped : LibraryEvent()
}