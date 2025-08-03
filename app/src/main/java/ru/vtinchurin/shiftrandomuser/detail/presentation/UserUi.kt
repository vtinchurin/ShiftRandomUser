package ru.vtinchurin.shiftrandomuser.detail.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserUi(
    val name: String = "",
    val imageUrl: String = "",
    val city: String = "",
    val phone: String = "",
    val email: String = "",
    val longitude: String = "",
    val latitude: String = "",
) : Parcelable