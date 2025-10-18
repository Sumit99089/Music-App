package com.example.music.presentation.library.components

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
import androidx.compose.material3.Card
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
import com.example.music.presentation.utils.formatTime

@Composable
fun SongItem(
    modifier: Modifier = Modifier,
    song: SongModel,
    onClick: (SongModel)->Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable{
                onClick(song)
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Card(
            modifier = Modifier.size(56.dp),
            shape = RoundedCornerShape(8.dp)
        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(song.contentUri)
                    .crossfade(true)
                    .build(),
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                placeholder = rememberVectorPainter(Icons.Default.MusicNote),
                contentDescription = "Album Art for ${song.title}",
                contentScale = ContentScale.Crop,
                error = rememberVectorPainter(Icons.Default.MusicNote),
            )
        }

        Spacer(Modifier.width(16.dp))

        Column (
            Modifier.weight(1f)
        ){
            Text(
                text= song.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = song.artist,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        Text(
            text = formatTime(song.duration),
            style = MaterialTheme.typography.bodySmall,
            color = Color.DarkGray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SongItemPreview() {
    MaterialTheme {
        SongItem(
            modifier = Modifier,
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