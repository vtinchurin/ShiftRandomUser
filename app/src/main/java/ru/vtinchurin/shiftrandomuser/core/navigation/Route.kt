package ru.vtinchurin.shiftrandomuser.core.navigation

import kotlinx.serialization.Serializable

interface Route

interface Screen {

    @Serializable
    object MainGraph : Route {

        @Serializable
        object UserList : Screen

        @Serializable
        data class UserDetail(val id: Long) : Screen
    }
}