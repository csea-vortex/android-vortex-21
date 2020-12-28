package edu.nitt.vortex2021.helpers

import java.text.SimpleDateFormat
import java.util.*

fun Date.getFormatted(): String {
    val format = SimpleDateFormat("dd MMM, HH:mm z", Locale("en", "IN"))
    return format.format(this)
}

