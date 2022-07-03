package com.example.agendamento.model

sealed class UserResponse<out T> {
    data class Success<out T>(val data: T) : UserResponse<T>()
    data class Error(val error: String) : UserResponse<Nothing>()
}