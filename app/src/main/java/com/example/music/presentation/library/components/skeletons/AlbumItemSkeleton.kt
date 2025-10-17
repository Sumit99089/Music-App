package com.example.music.presentation.library.components.skeletons

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun AlbumItemSkeleton(){
    Column(
        modifier = Modifier
            .padding(8.dp)
            .shimmer()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f )
                .clip(RoundedCornerShape(12.dp))
                .background( Color.Gray )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth(0.9f)
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth(0.6f)
                .background(Color.LightGray)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumItemSkeletonPreview() {
    MaterialTheme {
        AlbumItemSkeleton()
    }
}
