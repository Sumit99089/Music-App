package com.example.music.presentation.miniplayer

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.music.domain.models.SongModel
import com.example.music.presentation.library.LibraryEvent

@Composable
fun MiniPlayer(
    song: SongModel?,
    isPlaying: Boolean = false,
    onEvent: (LibraryEvent) -> Unit
) {
    if (song == null) return
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEvent(LibraryEvent.OnMiniPlayerTapped) },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card (
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(song.contentUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Album art",
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                    placeholder = rememberVectorPainter(Icons.Default.MusicNote),
                    error = rememberVectorPainter(Icons.Default.MusicNote),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(song.title, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(song.artist, style = MaterialTheme.typography.bodySmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            IconButton(onClick = { onEvent(LibraryEvent.OnPlayPauseClicked) }) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Play/Pause",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun MiniPlayerPreview() {
    MaterialTheme {
        MiniPlayer(
            song = SongModel(
                id = 1,
                title = "Song Title",
                artist = "Artist Name",
                contentUri = Uri.EMPTY,
                album = "Album Name",
                duration = 500
            ),
            onEvent = {}
        )
    }
}

