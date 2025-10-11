package com.example.music.presentation.library

import androidx.lifecycle.ViewModel
import com.example.music.domain.usecase.GetAllAlbumsUseCase
import com.example.music.domain.usecase.GetAllArtistsUseCase
import com.example.music.domain.usecase.GetAllSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getSongsUseCase: GetAllSongsUseCase,
    private val getArtistsUseCase: GetAllArtistsUseCase,
    private val getAlbumsUseCase: GetAllAlbumsUseCase
): ViewModel() {

    init{

    }

    fun onEvent(event: LibraryEvent){
        when(event){
            is
            LibraryEvent.LoadMusic -> TODO()
            LibraryEvent.OnMiniPlayerTapped -> TODO()
            LibraryEvent.OnPlayPauseClicked -> TODO()
            LibraryEvent.OnSearchClicked -> TODO()
            is LibraryEvent.OnSongClicked -> TODO()
            is LibraryEvent.OnTabSelected -> TODO()
        }
    }

    fun loadMusic(){
        getSongsUseCase()
    }
}