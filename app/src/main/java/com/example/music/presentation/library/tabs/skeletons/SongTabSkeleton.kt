package com.example.music.presentation.library.tabs.skeletons


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.music.presentation.library.components.skeletons.SongItemSkeleton

@Composable
fun SongTabSkeleton() {
    LazyColumn {
        items(10) { index ->// Display 10 placeholder items
            SongItemSkeleton()
            if (index <= 10) {
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
fun SongTabSkeletonPreview() {
    MaterialTheme {
        SongTabSkeleton()
    }
}