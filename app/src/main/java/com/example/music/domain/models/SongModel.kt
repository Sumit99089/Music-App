package com.example.music.domain.models

import android.net.Uri

data class SongModel(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val contentUri: Uri
)
