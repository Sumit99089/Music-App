package com.example.music.data.local

import android.net.Uri

data class SongEntity(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val contentUri: Uri
)