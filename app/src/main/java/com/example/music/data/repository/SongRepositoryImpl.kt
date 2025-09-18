package com.example.music.data.repository

import com.example.music.data.local.LocalMusicDataSource
import com.example.music.data.mapper.toSongModel
import com.example.music.domain.models.SongModel
import com.example.music.domain.repository.SongRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val localMusicDataSource: LocalMusicDataSource
): SongRepositoryInterface {
    override fun getAllSongs(): Flow<List<SongModel>> {
        return localMusicDataSource.getAllSongs().map { songEntityList->
            songEntityList.map{ songEntity->
                songEntity.toSongModel()
            }
        }
    }
}