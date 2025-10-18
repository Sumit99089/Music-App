package com.example.music.domain.usecase

import com.example.music.domain.models.ArtistModel
import com.example.music.domain.repository.SongRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllArtistsUseCase @Inject constructor(
    private val songRepository: SongRepositoryInterface
) {
    operator fun invoke(): Flow<List<ArtistModel>>{
        return songRepository.getAllArtists()
    }
}