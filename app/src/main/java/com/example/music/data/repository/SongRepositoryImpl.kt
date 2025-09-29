package com.example.music.data.repository

import com.example.music.data.local.LocalMusicDataSource
import com.example.music.data.mapper.toSongModel
import com.example.music.domain.models.AlbumDetailModel
import com.example.music.domain.models.AlbumModel
import com.example.music.domain.models.ArtistDetailModel
import com.example.music.domain.models.ArtistModel
import com.example.music.domain.models.SongModel
import com.example.music.domain.repository.SongRepositoryInterface
import kotlinx.coroutines.flow.Flow
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

    override fun getAllAlbums(): Flow<List<AlbumModel>> {
        return localMusicDataSource.getAllSongs().map { songEntityList ->
            songEntityList.groupBy { it.albumId }.map { (albumId, songs) ->
                val firstSong = songs.first()
                AlbumModel(
                    id = albumId,
                    name = firstSong.album,
                    artist = firstSong.artist,
                    artworkUri = firstSong.contentUri
                )
            }
        }
    }

    override fun getAllArtists(): Flow<List<ArtistModel>> {
        return localMusicDataSource.getAllSongs().map { songEntityList ->
            songEntityList.groupBy { it.artistId}.map { (artistId, songs) ->
                val firstSong = songs.first()
                ArtistModel(
                    id = artistId,
                    name = firstSong.artist,
                    songCount = songs.size
                )
            }
        }
    }

    override fun getAlbumSongs(albumId: Long): Flow<AlbumDetailModel> {
        return localMusicDataSource.getAllSongs().map { songEntityList ->
            val songsForAlbum = songEntityList
                .filter { it.albumId == albumId } // Find only the songs for this album
                .map { it.toSongModel() } // Map them to SongModel

            val firstSong = songEntityList.first { it.albumId == albumId }

            AlbumDetailModel(
                id = albumId,
                name = firstSong.album,
                artist = firstSong.artist,
                artworkUri = firstSong.contentUri,
                songs = songsForAlbum // Assign the filtered list of songs
            )
        }
    }

    override fun getArtistSongs(artistId: Long): Flow<ArtistDetailModel> {
        return localMusicDataSource.getAllSongs().map { songEntityList ->
            val songsOfArtist = songEntityList.filter{ it.artistId == artistId }.map {
                it.toSongModel()
            }
            val firstSong = songsOfArtist.first()
            ArtistDetailModel(
                id = artistId,
                name = firstSong.artist,
                songs = songsOfArtist
            )
        }
    }
}