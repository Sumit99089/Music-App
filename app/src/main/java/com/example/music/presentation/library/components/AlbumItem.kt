package com.example.music.presentation.library.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.music.domain.models.AlbumModel

@Composable
fun AlbumItem(
    album: AlbumModel,
    onClick: (AlbumModel) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick(album) }
    ) {
        // Card for album art with elevation and a 1:1 aspect ratio
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f), // Ensures the card is a perfect square
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(album.artworkUri)
                    .crossfade(true)
                    .build(),
                contentDescription = "Album art for ${album.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                placeholder = rememberVectorPainter(Icons.Default.Album),
                error = rememberVectorPainter(Icons.Default.Album)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Album Name
        Text(
            text = album.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // Artist Name
        Text(
            text = album.artist,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, widthDp = 200)
@Composable
fun AlbumItemPreview() {
    MaterialTheme {
        AlbumItem(
            album = AlbumModel(
                id = 1L,
                name = "Greatest Hits bvjfkhkhkhkhkgjgjjhjfhjgjgjg",
                artist = "A Very Famous Artistbkbjbkbkdbkskkwsjkwskwhkqhwikwikh",
                artworkUri = Uri.EMPTY
            ),
            onClick = {}
        )
    }
}