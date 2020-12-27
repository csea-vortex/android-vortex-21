package edu.nitt.vortex2021.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import edu.nitt.vortex2021.viewmodel.LeaderboardViewModel
import edu.nitt.vortex2021.viewmodel.ViewModelKey

@Module
abstract class LeaderboardModule {
    @Binds
    @IntoMap
    @ViewModelKey(LeaderboardViewModel::class)
    abstract fun bindLeaderboardModel(leaderboardViewModel: LeaderboardViewModel): ViewModel
}
