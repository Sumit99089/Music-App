package com.example.music


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import com.example.music.infrastructure.service.MusicServiceConnection
import com.example.music.presentation.library.LibraryScreen
import com.example.music.ui.theme.MusicTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var musicServiceConnection: MusicServiceConnection
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(
                                    text = "MusicPlayer"
                            )
                                    },
                        )
                    }
                ) { paddingValues ->
                    LibraryScreen(paddingValues = paddingValues)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        musicServiceConnection.release()
    }
}


