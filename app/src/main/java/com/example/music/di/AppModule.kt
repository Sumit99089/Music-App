package com.example.music.di

import android.content.Context
import com.example.music.data.local.LocalMusicDataSource
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
}