package edu.nitt.vortex2021.helpers

import android.content.Context
import androidx.core.content.edit

class UserSharedPrefStore(private val context: Context) {

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

    public var name: String
        get() = sharedPreferences.getString("name", "")!!
        set(value) {
            sharedPreferences.edit {
                putString("name", value)
                commit()
            }
        }


    public var username: String
        get() = sharedPreferences.getString("username", "")!!
        set(value) {
            sharedPreferences.edit {
                putString("username", value)
                commit()
            }
        }

    public var college: String
        get() = sharedPreferences.getString("college", "")!!
        set(value) {
            sharedPreferences.edit {
                putString("college", value)
                commit()
            }
        }

    public var email: String
        get() = sharedPreferences.getString("email", "")!!
        set(value) {
            sharedPreferences.edit {
                putString("email", value)
                commit()
            }
        }

    public var mobileNumber: Long
        get() = sharedPreferences.getLong("mobileNumber", 0)
        set(value) {
            sharedPreferences.edit {
                putLong("mobileNumber", value)
                commit()
            }
        }
}