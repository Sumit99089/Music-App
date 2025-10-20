package com.example.music.data.local

import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalMusicDataSource @Inject constructor(private val context: Context) {

    fun getAllSongs(): Flow<List<SongEntity>> {
        return flow {
            val songs = mutableListOf<SongEntity>()

            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION
            )
            // Select only those audio who are Music, ie, IS_MUSIC != 0
            val selectionClauses = mutableListOf<String>()

            // 1. Must be music
            selectionClauses.add("${MediaStore.Audio.Media.IS_MUSIC} != 0")

            // 2. Filter out ringtones, alarms, and notifications
            selectionClauses.add("${MediaStore.Audio.Media.IS_RINGTONE} = 0")
            selectionClauses.add("${MediaStore.Audio.Media.IS_ALARM} = 0")
            selectionClauses.add("${MediaStore.Audio.Media.IS_NOTIFICATION} = 0")

            // 3. Filter out recordings (available on Android 10 and up)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    selectionClauses.add("${MediaStore.Audio.Media.IS_RECORDING} = 0")
                }
            }

            // 4. Filter by minimum duration.
            // 60000 milliseconds = 1 minute. Adjust this as you like.
            val minDurationMs = 60000
            selectionClauses.add("${MediaStore.Audio.Media.DURATION} >= $minDurationMs")

            // Join all clauses with " AND "
            val selection = selectionClauses.joinToString(separator = " AND ")

            val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

            context.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                sortOrder
            )?.use{ cursor->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)

                val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)

                val artistIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID)

                val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

                val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)

                val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)

                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

                while(cursor.moveToNext()){
                    val id = cursor.getLong(idColumn)
                    val artistId = cursor.getLong(artistIdColumn)
                    val albumId = cursor.getLong(albumIdColumn)
                    val title = cursor.getString(titleColumn)
                    val artist = cursor.getString(artistColumn)
                    val album = cursor.getString(albumColumn)
                    val duration = cursor.getLong(durationColumn)

                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    songs.add(SongEntity(id, artistId, albumId,title, artist, album, duration, contentUri))
                }
            }
            emit(songs)
        }.flowOn(Dispatchers.IO)
    }
}