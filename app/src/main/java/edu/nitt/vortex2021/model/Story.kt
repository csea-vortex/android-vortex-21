package edu.nitt.vortex2021.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
class Story(
    @SerializedName("_id")
    val id: String,

    @SerializedName("shortDescription")
    val shortDescription: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("slides")
    val slides: List<StorySlide>,

    @SerializedName("visibleAt")
    val visibleAt: Date

) : Parcelable

@Parcelize
class Stories: ArrayList<Story>(), Parcelable