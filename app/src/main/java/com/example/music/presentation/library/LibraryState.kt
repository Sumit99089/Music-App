package com.example.music.presentation.library

import com.example.music.domain.models.AlbumModel
import com.example.music.domain.models.ArtistModel
import com.example.music.domain.models.SongModel

data class LibraryState(
    val isLoading: Boolean = true,
    val songs: List<SongModel> = emptyList(),
    val albums: List<AlbumModel> = emptyList(),
    val artists: List<ArtistModel> = emptyList(),
    val selectedTab: Tab = Tab.SONGS,
    val nowPlaying: SongModel? = null,
    val error: String? = null
)
