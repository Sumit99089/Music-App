package com.example.music.domain.models

data class ArtistModel(
    val id: Long,        // Unique ID for navigation
    val name: String,
    val songCount: Int   // Useful metadata for the list item
)