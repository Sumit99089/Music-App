package com.example.music.domain.models

import android.net.Uri

data class AlbumDetailModel(
    val id: Long,
    val name: String,
    val artist: String,
    val artworkUri: Uri?,
    val songs: List<SongModel> // The full list of songs for this album
)