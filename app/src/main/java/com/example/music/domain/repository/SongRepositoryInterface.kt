package com.example.music.domain.repository

import com.example.music.domain.models.AlbumDetailModel
import com.example.music.domain.models.AlbumModel
import com.example.music.domain.models.ArtistDetailModel
import com.example.music.domain.models.ArtistModel
import com.example.music.domain.models.SongModel
import kotlinx.coroutines.flow.Flow

interface SongRepositoryInterface {
    // For the main library screen (Songs Tab)
    fun getAllSongs(): Flow<List<SongModel>>

    // For the main library screen (Albums Tab) - RETURNS THE LIST MODEL
    fun getAllAlbums(): Flow<List<AlbumModel>>

    // For the main library screen (Artists Tab) - RETURNS THE LIST MODEL
    fun getAllArtists(): Flow<List<ArtistModel>>

    // For the Album Detail screen - TAKES AN ID, RETURNS THE DETAIL MODEL
    fun getAlbumSongs(albumId: Long): Flow<AlbumDetailModel>

    // For the Artist Detail screen - TAKES AN ID, RETURNS ALL SONGS OF ARTIST
    fun getArtistSongs(artistId: Long): Flow<ArtistDetailModel>
}