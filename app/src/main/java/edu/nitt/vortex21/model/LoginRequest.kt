package edu.nitt.vortex21.model

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String
)