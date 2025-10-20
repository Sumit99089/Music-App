package com.example.music


import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.music.domain.models.AlbumModel
import com.example.music.domain.models.ArtistModel
import com.example.music.domain.models.SongModel
import com.example.music.infrastructure.service.MusicServiceConnection
import com.example.music.presentation.library.LibraryScreen
import com.example.music.presentation.library.LibraryScreenContent
import com.example.music.presentation.library.LibraryState
import com.example.music.presentation.library.Tab
import com.example.music.presentation.miniplayer.MiniPlayer
import com.example.music.presentation.permissions.PermissionHandler
import com.example.music.ui.theme.MusicTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var musicServiceConnection: MusicServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicTheme {
                MusicAppContent()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        musicServiceConnection.release()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicAppContent() {
    PermissionHandler {
        // This content will only be shown if permissions are granted
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Music") })
            },
            bottomBar = {
                MiniPlayer()
            }
        ) { innerPadding ->
            LibraryScreen(paddingValues = innerPadding)
        }
    }
}



