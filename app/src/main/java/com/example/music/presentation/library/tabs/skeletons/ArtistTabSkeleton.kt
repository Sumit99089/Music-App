package com.example.music.presentation.library.tabs.skeletons


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.music.presentation.library.components.skeletons.ArtistItemSkeleton

@Composable
fun ArtistTabSkeleton() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(8) { // Display 8 placeholder items
            ArtistItemSkeleton()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtistTabSkeletonPreview() {
    MaterialTheme {
        ArtistTabSkeleton()
    }
}