package com.example.music.domain.usecase

import com.example.music.domain.models.AlbumDetailModel
import com.example.music.domain.repository.SongRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlbumSongsUseCase @Inject constructor(
    private val songRepository: SongRepositoryInterface
) {
    operator fun invoke(albumId: Long): Flow<AlbumDetailModel> {
        return songRepository.getAlbumSongs(albumId)
    }
}