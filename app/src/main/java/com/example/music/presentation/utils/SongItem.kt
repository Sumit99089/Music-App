package com.example.music.presentation.utils

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import java.util.concurrent.TimeUnit

@Composable
fun SongItem(
    song: SongModel,
    onClick: (SongModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(song) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Album Art
        Card(
            modifier = Modifier.size(56.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(song.contentUri) // Using content URI for album art
                    .crossfade(true)
                    .build(),
                contentDescription = "Album Art for ${song.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                // Placeholder for when there's no album art
                error = rememberVectorPainter(Icons.Default.MusicNote),
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Song Title and Artist
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = song.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = song.artist,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Duration
        Text(
            text = formatDuration(song.duration),
            style = MaterialTheme.typography.bodySmall,
            color = Color.DarkGray
        )
    }
}

// Helper function to format milliseconds into a user-friendly M:SS format
private fun formatDuration(millis: Long): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) -
            TimeUnit.MINUTES.toSeconds(minutes)
    return String.format("%d:%02d", minutes, seconds)
}

@Preview(showBackground = true)
@Composable
fun SongItemPreview() {
    MaterialTheme {
        SongItem(
            song = SongModel(
                id = 1L,
                title = "A Cool Song Title That Might Be Very Long",
                artist = "A Famous Artist",
                album = "Greatest Hits",
                duration = 215000L, // 3 minutes 35 seconds
                contentUri = Uri.EMPTY
            ),
            onClick = {}
        )
    }
}