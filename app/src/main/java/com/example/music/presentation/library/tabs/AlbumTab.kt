package com.example.music.presentation.library.tabs

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.music.domain.models.AlbumModel
import com.example.music.presentation.library.components.AlbumItem

@Composable
fun AlbumTab(
    albumList: List<AlbumModel>,
    onAlbumClick: (AlbumModel) -> Unit
){
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        // Display a fixed 2-column grid. For a responsive design that adapts
        // to screen size, you could use GridCells.Adaptive(minSize = 160.dp)
        columns = GridCells.Fixed(2),
        // Add padding around the entire grid
        contentPadding = PaddingValues(8.dp),
        // Add spacing between the grid items vertically
        verticalArrangement = Arrangement.spacedBy(8.dp),
        // Add spacing between the grid items horizontally
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = albumList,
            key = { album -> album.id }
        ){ album ->
            // Use your existing AlbumItem composable for each item in the list
            AlbumItem(
                album = album,
                onClick = { onAlbumClick(album) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumTabPreview() {
    // Create a list of sample albums for the preview
    val sampleAlbums = listOf(
        AlbumModel(1L, "A Night at the Opera", "Queen", Uri.EMPTY),
        AlbumModel(2L, "Led Zeppelin IV", "Led Zeppelin", Uri.EMPTY),
        AlbumModel(3L, "Hotel California", "Eagles", Uri.EMPTY),
        AlbumModel(4L, "Appetite for Destruction", "Guns N' Roses", Uri.EMPTY),
        AlbumModel(5L, "Back in Black", "AC/DC", Uri.EMPTY),
        AlbumModel(6L, "The Dark Side of the Moon", "Pink Floyd", Uri.EMPTY)
    )

    MaterialTheme {
        AlbumTab(
            albumList = sampleAlbums,
            onAlbumClick = {}
        )
    }
}