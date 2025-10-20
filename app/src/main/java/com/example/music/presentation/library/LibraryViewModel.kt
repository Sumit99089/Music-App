package com.example.music.presentation.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.music.domain.models.SongModel
import com.example.music.domain.usecase.GetAllAlbumsUseCase
import com.example.music.domain.usecase.GetAllArtistsUseCase
import com.example.music.domain.usecase.GetAllSongsUseCase
import com.example.music.infrastructure.service.MusicServiceConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getSongsUseCase: GetAllSongsUseCase,
    private val getArtistsUseCase: GetAllArtistsUseCase,
    private val getAlbumsUseCase: GetAllAlbumsUseCase,
    private val musicServiceConnection: MusicServiceConnection
): ViewModel() {
    private val _state =  MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()


    init{
        loadMusic()
    }

    fun onEvent(event: LibraryEvent){
        when(event){
            LibraryEvent.LoadMusic -> loadMusic()
            LibraryEvent.OnMiniPlayerTapped -> TODO()
            LibraryEvent.OnPlayPauseClicked -> TODO()
            LibraryEvent.OnSearchClicked -> TODO()
            is LibraryEvent.OnSongClicked -> {
                playMusic(event.song)
            }
            is LibraryEvent.OnTabSelected -> {
                _state.update { it.copy(selectedTab = event.tab, isLoading = false) }
            }
        }
    }

    private fun loadMusic(){
        viewModelScope.launch {
            _state.update { it.copy( isLoading = true ) }

            getSongsUseCase().onEach { songs->
                _state.update { it.copy(songs = songs) }
                musicServiceConnection.setMediaSongs(_state.value.songs)
            }.launchIn(viewModelScope)

            getArtistsUseCase().onEach { artists->
                _state.update { it.copy(artists = artists) }
            }.launchIn(viewModelScope)

            getAlbumsUseCase().onEach { albums->
                _state.update { it.copy(albums = albums) }
            }.launchIn(viewModelScope)

            _state.update { it.copy( isLoading = false ) }
        }
    }

    private fun playMusic(song: SongModel){
        musicServiceConnection.playSong( song )
        _state.update { it.copy( nowPlaying = song ) }

    }
}