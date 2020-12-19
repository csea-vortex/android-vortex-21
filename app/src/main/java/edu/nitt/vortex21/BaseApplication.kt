package edu.nitt.vortex21

import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import edu.nitt.vortex21.di.DaggerAppComponent

class BaseApplication : DaggerApplication() {
    val applicationComponent = DaggerAppComponent.factory().create(this)

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return applicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(this))

        val built = builder.build()
        built.setIndicatorsEnabled(true)
        built.isLoggingEnabled = true
        Picasso.setSingletonInstance(built)
    }

}