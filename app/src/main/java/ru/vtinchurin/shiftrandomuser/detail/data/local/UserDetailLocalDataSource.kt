package ru.vtinchurin.shiftrandomuser.detail.data.local

import ru.vtinchurin.shiftrandomuser.core.local.dao.UserDao
import ru.vtinchurin.shiftrandomuser.core.local.entity.UserAndImage
import ru.vtinchurin.shiftrandomuser.utils.Result
import ru.vtinchurin.shiftrandomuser.utils.mapError
import ru.vtinchurin.shiftrandomuser.utils.runOperationCatching

interface UserDetailLocalDataSource {
    suspend fun getUserDetail(id: Long): Result<UserAndImage, Throwable>

    class Base(
        private val userDao: UserDao,
    ) : UserDetailLocalDataSource {
        override suspend fun getUserDetail(id: Long): Result<UserAndImage, Throwable> =
            runOperationCatching {
                userDao.loadUserDetail(id = id)
            }.mapError { throwable ->
                IllegalStateException("User detail not found", throwable)
            }
    }
}