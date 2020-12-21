package edu.nitt.vortex2021.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import edu.nitt.vortex2021.viewmodel.DataViewModel
import edu.nitt.vortex2021.viewmodel.ViewModelKey

@Module
abstract class DataModule {
    @Binds
    @IntoMap
    @ViewModelKey(DataViewModel::class)
    abstract fun bindDataViewModel(dataViewModel: DataViewModel): ViewModel
}
