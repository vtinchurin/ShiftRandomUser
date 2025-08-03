package ru.vtinchurin.shiftrandomuser.detail.domain.model

data class UserDetail(
    val id: Long,
    val name: String,
    val phone: String,
    val imageUrl: String,
    val email: String,
    val city: String,
    val longitude: String,
    val latitude: String,
)

