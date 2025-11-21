package com.example.presentation.data

data class LoginState(
    val email:String,
    val password:String,
    val isEmailValid: Boolean,
    val isPasswordValid: Boolean,
    val isActive: Boolean,
)