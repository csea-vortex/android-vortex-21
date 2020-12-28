package edu.nitt.vortex2021.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class EventData(
    @SerializedName("cost") val cost: Int,
    @SerializedName("prizeMoney") val prizeMoney: List<Int>,
    @SerializedName("_id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("isFree") val isFree: Boolean,
    @SerializedName("isNITTFree") val isNITTFree: Boolean,
    @SerializedName("image") val image: String,
    @SerializedName("link") val link: String,
    @SerializedName("rules") val rules: String,
    @SerializedName("resources") val resources: String,
    @SerializedName("format") val format: String,
    @SerializedName("eventFrom") val eventFrom: Date,
    @SerializedName("eventTo") val eventTo: Date,
    @SerializedName("smallImage") val smallImage: String
) : Parcelable