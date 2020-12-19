package edu.nitt.vortex2021.di

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
object AppModule {
    @Provides
    fun provideApplicationContext(app: Application) = app.applicationContext
}