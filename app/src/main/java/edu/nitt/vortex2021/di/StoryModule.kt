package edu.nitt.vortex2021.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import edu.nitt.vortex2021.viewmodel.StoryViewModel
import edu.nitt.vortex2021.viewmodel.ViewModelKey

@Module
abstract class StoryModule {
    @Binds
    @IntoMap
    @ViewModelKey(StoryViewModel::class)
    abstract fun bindStoryViewModel(storyViewModel: StoryViewModel): ViewModel
}