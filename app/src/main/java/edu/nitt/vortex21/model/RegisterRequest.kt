package edu.nitt.vortex21.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name")
    val fullName: String,

    val username: String,

    val password: String,

    @SerializedName("mobile")
    val mobileNumber: String,

    val email: String,

    val college: String,

    @SerializedName("dept")
    val department: String
)