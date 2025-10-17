package com.example.music.presentation.library

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.music.domain.models.AlbumModel
import com.example.music.domain.models.ArtistModel
import com.example.music.domain.models.SongModel
import com.example.music.presentation.library.tabs.AlbumTab
import com.example.music.presentation.library.tabs.ArtistTab
import com.example.music.presentation.library.tabs.SongTab
import com.example.music.ui.theme.MusicTheme
import java.util.Locale


@Composable
fun LibraryScreen(
    libraryViewModel: LibraryViewModel = hiltViewModel(),
    paddingValues: PaddingValues
){
    val libraryState by libraryViewModel.state.collectAsStateWithLifecycle()
    LibraryScreenContent(
        state = libraryState,
        onEvent = libraryViewModel::onEvent,
        paddingValues = paddingValues
    )
}
@Composable
fun LibraryScreenContent(
    state: LibraryState,
    onEvent: (LibraryEvent) -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        PrimaryTabRow(
            selectedTabIndex = state.selectedTab.ordinal,
            modifier = Modifier.padding(vertical = 12.dp),
            divider = {},
            indicator = {}
        ) {
            Tab.entries.forEach { tab ->
                val isSelected = state.selectedTab == tab
                val tabText = tab.name.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) }
                Tab(
                    selected = state.selectedTab == tab,
                    onClick = { onEvent(LibraryEvent.OnTabSelected( tab )) },
                ) {
                    Text(
                        text = tabText,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50)) // Clip the shape first
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent
                            )
                            .border(
                                width = 1.5.dp,
                                color = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                        ,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        PullToRefreshBox(
            isRefreshing = state.isLoading,
            onRefresh = { onEvent(LibraryEvent.LoadMusic) },
            indicator = {}
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(BiasAlignment(0f, -0.1f)))
                } else {
                    when (state.selectedTab) {
                        Tab.SONGS -> SongTab(
                            songList = state.songs,
                            onSongClicked = { onEvent(LibraryEvent.OnTabSelected(Tab.SONGS)) }
                        )
                        Tab.ARTISTS -> ArtistTab(
                            artistList = state.artists,
                            onArtistClick = { /* TODO: Fire event to navigate */ }
                        )
                        Tab.ALBUMS -> AlbumTab(
                            albumList = state.albums,
                            onAlbumClick = { /* TODO: Fire event to navigate */ }
                        )
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun LibraryScreenPreview() {
    val sampleSongs = listOf(
        SongModel(1L, "Bohemian Rhapsody", "Queen", "A Night at the Opera", 355000L, Uri.EMPTY),
        SongModel(2L, "Stairway to Heaven", "Led Zeppelin", "Led Zeppelin IV", 482000L, Uri.EMPTY),
        SongModel(3L, "Bohemian Rhapsody", "Queen", "A Night at the Opera", 355000L, Uri.EMPTY),
        SongModel(4L, "Stairway to Heaven", "Led Zeppelin", "Led Zeppelin IV", 482000L, Uri.EMPTY)
    )
    val sampleArtists = listOf(
        ArtistModel(id = 1L, name = "Queen", songCount = 25),
        ArtistModel(id = 2L, name = "Led Zeppelin", songCount = 18),
        ArtistModel(id = 3L, name = "Eagles", songCount = 15)
    )
    val sampleAlbums = listOf(
        AlbumModel(1L, "A Night at the Operagjgkhkjkjkjjk", "Queen", Uri.EMPTY),
        AlbumModel(2L, "Led Zeppelin IV", "Led Zeppelin", Uri.EMPTY)
    )
    val nowPlayingSong = SongModel(1L, "Bohemian Rhapsody", "Queen", "A Night at the Opera", 355000L, Uri.EMPTY)


    val fakeState = LibraryState(
        isLoading = false,
        songs = sampleSongs,
        artists = sampleArtists,
        albums = sampleAlbums,
        selectedTab = Tab.SONGS, // Start on the artist tab
        nowPlaying = nowPlayingSong // Show the mini-player
    )

    MusicTheme {
        LibraryScreenContent(
            state = fakeState,
            onEvent = {},
            paddingValues = PaddingValues(0.dp)
        )
    }
}



