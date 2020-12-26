package edu.nitt.vortex2021.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import edu.nitt.vortex2021.viewmodel.EventListViewModel
import edu.nitt.vortex2021.viewmodel.ViewModelKey

@Module
abstract class EventListModule {
    @Binds
    @IntoMap
    @ViewModelKey(EventListViewModel::class)
    abstract fun bindEventListViewModel(eventListViewModel: EventListViewModel) : ViewModel

}