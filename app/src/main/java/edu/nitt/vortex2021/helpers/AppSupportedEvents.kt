package edu.nitt.vortex2021.helpers

import java.util.*

enum class AppSupportedEvents {
    LINKED, NOT_SUPPORTED
}

fun getEventFromTitle(eventTitle: String): AppSupportedEvents {
    val title = eventTitle.toLowerCase(Locale.getDefault())
    if (title.contains("linked")) {
        return AppSupportedEvents.LINKED
    }

    return AppSupportedEvents.NOT_SUPPORTED
}