package edu.nitt.vortex2021.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import edu.nitt.vortex2021.BaseApplication
import edu.nitt.vortex2021.helpers.Constants
import edu.nitt.vortex2021.repository.*
import edu.nitt.vortex2021.viewmodel.ViewModelProviderFactory
import edu.nitt.vortex2021.viewmodel.ViewModelProviderModule
import javax.inject.Named
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelProviderModule::class,
        VortexApiModule::class,
        AppModule::class,
        StoryModule::class,
        AuthModule::class,
        UserModule::class,
        DataModule::class,
        LinkedModule::class,
        LeaderboardModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<BaseApplication> {
    fun inject(repo: AuthRepository)
    fun inject(repo: UserRepository)
    fun inject(repo: StoryRepository)
    fun inject(repo: DataRepository)
    fun inject(repo: LinkedRepository)
    fun inject(repo: LeaderboardRepository)

    fun getViewModelProviderFactory(): ViewModelProviderFactory

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance
            application: Application,

            @BindsInstance
            @Named("url")
            url: String = Constants.API_BASE_URL
        ): AppComponent
    }
}