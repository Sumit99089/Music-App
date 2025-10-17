package com.example.music.presentation.library.tabs

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
import com.example.music.domain.models.ArtistModel
import com.example.music.presentation.library.components.ArtistItem

@Composable
fun ArtistTab(
    modifier: Modifier = Modifier,
    artistList: List<ArtistModel>,
    onArtistClick: (ArtistModel) -> Unit
){
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = artistList,
            key = { artist -> artist.id }
        ){ artist->
            ArtistItem(
                artist = artist,
                onClick = { onArtistClick(artist) }
            )
        }
    }
}

// The Preview for the ArtistTab
@Preview(showBackground = true)
@Composable
fun ArtistTabPreview() {
    // 1. Create fake data for the preview to display
    val sampleArtists = listOf(
        ArtistModel(id = 1L, name = "Queen", songCount = 25),
        ArtistModel(id = 2L, name = "Led Zeppelin", songCount = 18),
        ArtistModel(id = 3L, name = "The Eagles", songCount = 12),
        ArtistModel(id = 4L, name = "Aerosmith", songCount = 31),
        ArtistModel(id = 5L, name = "A very long artist name to check wrapping", songCount = 5)
    )

    // 2. Wrap the composable in a theme
    MaterialTheme {
        ArtistTab(
            artistList = sampleArtists,
            onArtistClick = {} // Clicks do nothing in a preview
        )
    }
}
