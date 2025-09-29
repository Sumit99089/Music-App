package com.example.music.domain.models

import android.net.Uri

data class AlbumModel(
    val id: Long,            // Unique ID for navigation and list keys
    val name: String,
    val artist: String,
    val artworkUri: Uri?     // URI to load the album cover
)