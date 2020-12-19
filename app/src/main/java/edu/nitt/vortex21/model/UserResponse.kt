package edu.nitt.vortex21.model

import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import com.google.gson.annotations.SerializedName

data class UserResponse(

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: User
)