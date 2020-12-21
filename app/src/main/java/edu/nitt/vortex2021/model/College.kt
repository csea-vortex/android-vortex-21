package edu.nitt.vortex2021.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class College(
    @SerializedName("_id")
    val id: String,

    @SerializedName("name")
    val name: String,

) : Parcelable

@Parcelize
class Colleges: ArrayList<Story>(), Parcelable
