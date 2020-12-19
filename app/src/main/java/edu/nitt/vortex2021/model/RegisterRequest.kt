package edu.nitt.vortex2021.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name")
    val fullName: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("mobile")
    val mobileNumber: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("college")
    val college: String,

    @SerializedName("dept")
    val department: String
)