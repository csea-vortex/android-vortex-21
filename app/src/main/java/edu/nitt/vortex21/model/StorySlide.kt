package edu.nitt.vortex21.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class StorySlide (
    @SerializedName("_id")
    val id: String,

    @SerializedName("path")
    val imageUrl: String,

    @SerializedName("visibleAt")
    val visibleAt: Date
) : Parcelable