package edu.nitt.vortex.model

import androidx.annotation.DrawableRes

class SocialMediaLink(
    val name: String,
    @DrawableRes val iconId: Int,
    val url: String,
)