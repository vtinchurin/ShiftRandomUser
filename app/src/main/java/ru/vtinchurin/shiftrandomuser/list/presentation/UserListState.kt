package ru.vtinchurin.shiftrandomuser.list.presentation

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class UserListState(
    val users: List<UserUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
) : Parcelable

