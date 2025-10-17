package com.example.music.presentation.library.components.skeletons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun SongItemSkeleton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .shimmer(), // Apply shimmer to the whole row
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
        )
        Spacer(Modifier.width(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(200.dp)
                    .background(Color.LightGray)

            )
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .width(100.dp)
                    .background(Color.LightGray)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SongItemSkeletonPreview() {
    MaterialTheme {
        SongItemSkeleton()
    }
}