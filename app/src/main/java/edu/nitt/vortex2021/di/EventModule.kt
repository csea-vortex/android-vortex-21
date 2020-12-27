package edu.nitt.vortex2021.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import edu.nitt.vortex2021.viewmodel.EventViewModel
import edu.nitt.vortex2021.viewmodel.ViewModelKey

@Module
abstract class EventModule {
    @Binds
    @IntoMap
    @ViewModelKey(EventViewModel::class)
    abstract fun bindEventListViewModel(eventViewModel: EventViewModel) : ViewModel

}