package edu.nitt.vortex21.model

data class RegisterResponse (
    val success: Boolean,
    val message: String,
    val token: String
)