package com.example.music.di

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC
import androidx.media3.common.C.USAGE_MEDIA
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @Provides
    @ServiceScoped
    fun provideExoPlayer(@ApplicationContext context: Context, audioAttributes: AudioAttributes): ExoPlayer {
        return ExoPlayer.Builder(context).setAudioAttributes(audioAttributes, true).setHandleAudioBecomingNoisy(true).build()
    }

    @Provides
    @ServiceScoped
    fun provideAudioAttributes(): AudioAttributes {
        return AudioAttributes.Builder().setContentType(AUDIO_CONTENT_TYPE_MUSIC).setUsage(USAGE_MEDIA).build()
    }
}