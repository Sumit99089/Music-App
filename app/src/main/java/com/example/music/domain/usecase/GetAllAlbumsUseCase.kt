package com.example.music.domain.usecase

import com.example.music.domain.models.AlbumModel
import com.example.music.domain.repository.SongRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAlbumsUseCase @Inject constructor(
    private val songRepository: SongRepositoryInterface
) {
    operator fun invoke(): Flow<List<AlbumModel>> {
        return songRepository.getAllAlbums()
    }
}