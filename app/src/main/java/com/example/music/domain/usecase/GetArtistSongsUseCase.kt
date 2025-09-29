package com.example.music.domain.usecase

import com.example.music.domain.models.ArtistDetailModel
import com.example.music.domain.models.SongModel
import com.example.music.domain.repository.SongRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtistSongsUseCase @Inject constructor(
    private val songRepository: SongRepositoryInterface
) {
    operator fun invoke(artistId: Long): Flow<ArtistDetailModel> {
        return songRepository.getArtistSongs(artistId)
    }
}