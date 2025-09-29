package com.example.music.domain.models

data class ArtistDetailModel(
    val id: Long,
    val name: String,
    val songs: List<SongModel> // All songs by this artist
)