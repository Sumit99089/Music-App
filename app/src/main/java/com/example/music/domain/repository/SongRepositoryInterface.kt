package com.example.music.domain.repository

import com.example.music.domain.models.SongModel
import kotlinx.coroutines.flow.Flow

interface SongRepositoryInterface {
    fun getAllSongs(): Flow<List<SongModel>>
}