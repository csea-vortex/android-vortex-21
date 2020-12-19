package edu.nitt.vortex2021.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import edu.nitt.vortex2021.viewmodel.AuthViewModel
import edu.nitt.vortex2021.viewmodel.ViewModelKey

@Module
abstract class AuthModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel
}
