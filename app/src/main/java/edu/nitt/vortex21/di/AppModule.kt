package edu.nitt.vortex21.di

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
object AppModule {
    @Provides
    fun provideApplicationContext(app: Application) = app.applicationContext
}