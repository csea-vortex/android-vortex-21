package edu.nitt.vortex21.helpers

import edu.nitt.vortex21.R
import edu.nitt.vortex21.model.SocialMediaLink

object Constants {
    val departments = listOf(
        "Architecture",
        "Chemical Engineering",
        "Chemistry",
        "Civil Engineering",
        "Computer Applications",
        "Computer Science and Engineering",
        "Electrical and Electronics Engineering",
        "Electronics and Communication Engineering",
        "Energy and Environment (CEESAT)",
        "Humanities and Social Sciences",
        "Information Technology",
        "Instrumentation and Control Engineering",
        "Management Studies",
        "Mathematics",
        "Mechanical Engineering",
        "Metallurgical and Materials Engineering",
        "Physics",
        "Production Engineering",
        "Other"
    )

    val yearOfStudy = listOf(
        "1", "2", "3", "4", "5",
        "Other"
    )

    const val SHARED_PREFERENCE = "encrypted_shared_pref"
    const val BACKEND_BASE_URL = "https://vortex.nitt.edu"
    const val API_BASE_URL = "${BACKEND_BASE_URL}/api/"

    val SOCIAL_MEDIA_LINKS = arrayOf(
        SocialMediaLink(
            "Vortex Website",
            R.drawable.csea_logo,
            "https://vortex.nitt.edu/"
        ),
        SocialMediaLink(
            "Facebook",
            R.drawable.facebook,
            "https://www.facebook.com/vortex.nitt/"
        ),
        SocialMediaLink(
            "Instagram",
            R.drawable.instagram,
            "https://instagram.com/vortex_nitt?igshid=7cduw1t8j1k"
        ),
        SocialMediaLink(
            "Twitter",
            R.drawable.twitter,
            ""
        ),
        SocialMediaLink(
            "LinkedIn",
            R.drawable.linkedin,
            ""
        ),
    )
}