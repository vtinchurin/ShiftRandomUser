package ru.vtinchurin.shiftrandomuser.detail.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetailScreenState(
    val user: UserUi = UserUi(),
    val isLoading: Boolean = true,
    val error: String? = null,
) : Parcelable