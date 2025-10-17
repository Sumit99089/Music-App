package com.example.music.presentation.library.tabs

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.music.domain.models.SongModel
import com.example.music.presentation.library.components.SongItem

@Composable
fun SongTab(
    modifier: Modifier = Modifier,
    songList: List<SongModel>,
    onSongClicked: (SongModel) -> Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = songList,
            key = { _, song -> song.id }
        ){ index, song ->
            SongItem(
                song = song,
                onClick = { onSongClicked(song)}
            )
            if (index < songList.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SongListPreview() {
    val sampleSongs = listOf(
        SongModel(1L, "Bohemian Rhapsody", "Queen", "A Night at the Opera", 355000L, Uri.EMPTY),
        SongModel(2L, "Stairway to Heaven", "Led Zeppelin", "Led Zeppelin IV", 482000L, Uri.EMPTY),
        SongModel(3L, "Hotel California", "Eagles", "Hotel California", 391000L, Uri.EMPTY),
        SongModel(4L, "Sweet Child O' Mine", "Guns N' Roses", "Appetite for Destruction", 356000L, Uri.EMPTY),
        SongModel(5L, "Bohemian Rhapsody", "Queen", "A Night at the Opera", 355000L, Uri.EMPTY),
        SongModel(6L, "Stairway to Heaven", "Led Zeppelin", "Led Zeppelin IV", 482000L, Uri.EMPTY),
        )

    MaterialTheme {
        SongTab(
            songList = sampleSongs,
            onSongClicked = {} // The click action does nothing in a preview
        )
    }
}