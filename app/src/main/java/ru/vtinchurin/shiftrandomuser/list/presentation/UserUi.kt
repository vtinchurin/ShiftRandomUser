package ru.vtinchurin.shiftrandomuser.list.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.vtinchurin.shiftrandomuser.list.domain.model.User

@Parcelize
data class UserUi(
    val id: Long = 0,
    val name: String,
    val city: String,
    val phone: String,
    val email: String,
    val imageUrl: String,
) : Parcelable

fun User.toUi() = UserUi(
    id = id,
    name = name,
    city = city,
    phone = phone,
    email = email,
    imageUrl = imageUrl
)