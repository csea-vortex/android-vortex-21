package edu.nitt.vortex.helpers

import android.util.Patterns

object Validators {
    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPhoneNumber(number: String): Boolean {
        return Patterns.PHONE.matcher(number).matches()
    }

    fun isAlphaNumeric(username: String): Boolean {
        return username.matches("^[a-zA-Z0-9]*$".toRegex())
    }

    fun containsAlphabets(name: String): Boolean {
        return name.matches("^[a-zA-Z]*$".toRegex())
    }
}