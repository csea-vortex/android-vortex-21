package edu.nitt.vortex2021.api

import android.content.Context
import edu.nitt.vortex2021.helpers.UserTokenStore
import okhttp3.Interceptor
import okhttp3.Response

class CookieAuthTokenInterceptor(private val context: Context) : Interceptor {
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
