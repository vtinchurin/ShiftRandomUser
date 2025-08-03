package ru.vtinchurin.shiftrandomuser.list.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.vtinchurin.shiftrandomuser.list.domain.model.User
import ru.vtinchurin.shiftrandomuser.utils.Result
import ru.vtinchurin.shiftrandomuser.utils.VoidResult

interface UserListRepository {

    suspend fun refreshUsers(): VoidResult<String>

    fun getUsers(): Flow<Result<List<User>, String>>
}