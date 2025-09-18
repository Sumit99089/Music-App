package com.example.music.data.mapper

import com.example.music.data.local.SongEntity
import com.example.music.domain.models.SongModel

fun SongEntity.toSongModel(): SongModel {
    return SongModel(
        id = id,
        title = title,
        artist = artist,
        album = album,
        duration = duration,
        contentUri = contentUri
    )
}
