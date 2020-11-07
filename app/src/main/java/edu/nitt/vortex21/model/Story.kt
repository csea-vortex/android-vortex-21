package edu.nitt.vortex21.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Story(
    public var imageurl:List<String>,
    public var storyid:String = "",
    public var storyName:String = ""
):Parcelable
