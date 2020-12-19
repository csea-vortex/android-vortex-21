package edu.nitt.vortex21.model

data class User(
    val name: String,
    val username: String,
    val email: String,
    val department: String,
    val mobile: Long,
    val college: String,
    val isVerified: Boolean
)