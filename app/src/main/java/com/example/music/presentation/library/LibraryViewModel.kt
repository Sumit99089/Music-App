package com.example.music.presentation.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.music.domain.usecase.GetAllAlbumsUseCase
import com.example.music.domain.usecase.GetAllArtistsUseCase
import com.example.music.domain.usecase.GetAllSongsUseCase
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
    private val getAlbumsUseCase: GetAllAlbumsUseCase
): ViewModel() {
    private val _state =  MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()


    init{
        loadMusic()
    }

    fun onEvent(event: LibraryEvent){
        when(event){
            LibraryEvent.LoadMusic -> TODO()
            LibraryEvent.OnMiniPlayerTapped -> TODO()
            LibraryEvent.OnPlayPauseClicked -> TODO()
            LibraryEvent.OnSearchClicked -> TODO()
            is LibraryEvent.OnSongClicked -> TODO()
            is LibraryEvent.OnTabSelected -> TODO()
        }
    }

    private fun loadMusic(){
        viewModelScope.launch {
            _state.update { it.copy( isLoading = true ) }

            getSongsUseCase().onEach { songs->
                _state.update { it.copy(songs = songs) }
            }.launchIn(viewModelScope)

            getArtistsUseCase().onEach { artists->
                _state.update { it.copy(artists = artists) }
            }.launchIn(viewModelScope)

            getAlbumsUseCase().onEach { albums->
                _state.update { it.copy(albums = albums) }
            }.launchIn(viewModelScope)
        }
    }
}