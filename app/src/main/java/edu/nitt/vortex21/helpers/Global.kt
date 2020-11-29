package edu.nitt.vortex21.helpers

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso


class Global : Application() {
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
