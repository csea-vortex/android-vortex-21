package edu.nitt.vortex21.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import edu.nitt.vortex21.BaseApplication
import edu.nitt.vortex21.helpers.Constants
import edu.nitt.vortex21.repository.AuthRepository
import edu.nitt.vortex21.repository.StoryRepository
import edu.nitt.vortex21.repository.UserRepository
import edu.nitt.vortex21.viewmodel.ViewModelProviderFactory
import edu.nitt.vortex21.viewmodel.ViewModelProviderModule
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
        UserModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<BaseApplication> {
    fun inject(repo: AuthRepository)
    fun inject(repo: UserRepository)
    fun inject(repo: StoryRepository)

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