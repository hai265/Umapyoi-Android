package com.example.uma.data.network

import android.content.Context
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.request.crossfade
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoilModule {
    companion object {
        @Provides
        fun providesSingletonImageLoader(@ApplicationContext context: Context): ImageLoader =
            ImageLoader.Builder(context)
                .crossfade(true)
                .build()
    }
}