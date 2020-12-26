package edu.nitt.vortex2021.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import edu.nitt.vortex2021.viewmodel.LinkedViewModel
import edu.nitt.vortex2021.viewmodel.ViewModelKey

@Module
abstract class LinkedModule {
    @Binds
    @IntoMap
    @ViewModelKey(LinkedViewModel::class)
    abstract fun bindLinkedViewModel(linkedViewModel: LinkedViewModel): ViewModel
}