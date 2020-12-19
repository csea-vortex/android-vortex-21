package edu.nitt.vortex2021.model

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: User
)