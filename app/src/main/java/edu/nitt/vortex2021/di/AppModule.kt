package edu.nitt.vortex2021.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
object AppModule {
    @Provides
    fun provideApplicationContext(app: Application): Context = app.applicationContext
}