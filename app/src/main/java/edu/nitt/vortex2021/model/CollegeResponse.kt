package edu.nitt.vortex2021.model

import com.google.gson.annotations.SerializedName

data class CollegeResponse(
    @SerializedName("collegeList")
    val collegeList: List<College>,

    @SerializedName("listLength")
    val listLength: Int
)