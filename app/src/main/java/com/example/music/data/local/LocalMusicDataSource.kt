package com.example.music.data.local

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalMusicDataSource @Inject constructor(private val context: Context) {

    fun getAllSongs(): Flow<List<SongEntity>> {
        return flow {
            val songs = mutableListOf<SongEntity>()

            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION
            )
            // Select only those audio who are Music, ie, IS_MUSIC != 0
//            val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"

            val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

            context.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
//                selection,
                null,
                null,
                sortOrder
            )?.use{ cursor->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)

                val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)

                val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)

                val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)

                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

                while(cursor.moveToNext()){
                    val id = cursor.getLong(idColumn)
                    val title = cursor.getString(titleColumn)
                    val artist = cursor.getString(artistColumn)
                    val album = cursor.getString(albumColumn)
                    val duration = cursor.getLong(durationColumn)

                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    songs.add(SongEntity(id, title, artist, album, duration, contentUri))
                }
            }
            emit(songs)
        }.flowOn(Dispatchers.IO)
    }
}