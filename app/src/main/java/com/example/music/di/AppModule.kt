package com.example.music.di

import android.content.Context
import com.example.music.data.local.LocalMusicDataSource
import com.example.music.infrastructure.service.MusicServiceConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalMusicDataSource(@ApplicationContext context: Context): LocalMusicDataSource {
        return LocalMusicDataSource(context)
    }

    @Provides
    @Singleton
    fun provideMusicServiceConnection(@ApplicationContext context: Context): MusicServiceConnection {
        return MusicServiceConnection(context)
    }
}