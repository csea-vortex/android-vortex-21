package edu.nitt.vortex2021.helpers

import android.content.Context
import androidx.core.content.edit

class UserTokenStore(private val context: Context) {

    private val sharedPreferences= context.getSharedPreferences(
        Constants.SHARED_PREFERENCE,
        Context.MODE_PRIVATE
    )

    public var token: String
        get() = sharedPreferences.getString("token", "")!!
        set(value) {
            sharedPreferences.edit {
                putString("token", value)
                commit()
            }
        }
}