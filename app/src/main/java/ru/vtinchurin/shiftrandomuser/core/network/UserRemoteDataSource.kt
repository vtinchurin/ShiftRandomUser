package ru.vtinchurin.shiftrandomuser.core.network

import ru.vtinchurin.shiftrandomuser.core.network.model.UserInfo
import ru.vtinchurin.shiftrandomuser.utils.Result
import ru.vtinchurin.shiftrandomuser.utils.runOperationCatching

interface UserRemoteDataSource {

    suspend fun getUsers(): Result<List<UserInfo>, Throwable>

    class Base(
        private val service: UserService,
    ) : UserRemoteDataSource {
        override suspend fun getUsers(): Result<List<UserInfo>, Throwable> =
            runOperationCatching {
                service.getUsers().results
            }
    }
}