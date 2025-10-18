package com.example.music.presentation.utils

import java.util.Locale
import java.util.concurrent.TimeUnit

fun formatTime(timeInMilis: Long):String{
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMilis)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMilis) - TimeUnit.MINUTES.toSeconds(minutes)

    return String.Companion.format(Locale.US,"%d:%02d", minutes, seconds)
}