package edu.nitt.vortex21.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("department")
    val department: String,

    @SerializedName("mobile")
    val mobile: Long,

    @SerializedName("college")
    val college: String,

    @SerializedName("isVerified")
    val isVerified: Boolean
)