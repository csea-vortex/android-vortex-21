package edu.nitt.vortex21.model

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,
)