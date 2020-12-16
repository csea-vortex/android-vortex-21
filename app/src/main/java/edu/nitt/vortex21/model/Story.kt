package edu.nitt.vortex21.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Story(
    @SerializedName("_id")
    val id: String,

    val shortDescription: String,
    val description: String,
    val title: String,
    val category: String,
    val slides: List<StorySlide>
) : Parcelable

@Parcelize
class Stories: ArrayList<Story>(), Parcelable