package edu.nitt.vortex21.api

import android.content.Context
import android.util.Log
import edu.nitt.vortex21.helpers.UserTokenStore
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val userTokenStore = UserTokenStore(context)
        proceed(
            request()
                .newBuilder()
                .addHeader("Cookie", "token=${userTokenStore.token}")
                .build()
        ).apply {
            val cookies = headers("Set-Cookie")
            for (cookie in cookies) {
                if (cookie.startsWith("token=")) {
                    userTokenStore.token = cookie.substring("token=".length)
                }
            }
        }
    }
}
