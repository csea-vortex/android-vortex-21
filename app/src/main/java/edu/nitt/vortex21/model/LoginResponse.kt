package edu.nitt.vortex21.model

data class LoginResponse (
    val success: Boolean,
    val message: String,
    val token: String
)