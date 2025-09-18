package com.example.music.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.music.domain.usecase.GetAllSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
        private val getAllSongsUseCase: GetAllSongsUseCase
) : ViewModel() {

    // Use an init block or a function to call the use case
    init {
        fetchSongs()
    }

    private fun fetchSongs() {
        // Launch a coroutine in the ViewModel's own lifecycle-aware scope
        viewModelScope.launch {
            // Now you can safely call the use case
            // The .collect() operator is what actually runs the Flow
            getAllSongsUseCase().collect { songList ->
                // Do something with the list of songs
                Log.d("MusicViewModel", songList.toString())
            }
        }
    }
}