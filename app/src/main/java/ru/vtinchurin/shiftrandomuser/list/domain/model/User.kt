package ru.vtinchurin.shiftrandomuser.list.domain.model

data class User(
    val id: Long,
    val name: String,
    val city: String,
    val phone: String,
    val imageUrl: String,
    val email: String,
)