package ru.vtinchurin.shiftrandomuser.list.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vtinchurin.shiftrandomuser.core.local.dao.UserDao
import ru.vtinchurin.shiftrandomuser.core.local.entity.ImageCache
import ru.vtinchurin.shiftrandomuser.core.local.entity.UserAndImage
import ru.vtinchurin.shiftrandomuser.core.local.entity.UserCache
import ru.vtinchurin.shiftrandomuser.utils.Result
import ru.vtinchurin.shiftrandomuser.utils.runOperationCatching

interface UserListLocalDataSource {
    fun getUsers(): Flow<Result<List<UserAndImage>, Throwable>>
    suspend fun saveUsers(users: List<UserCache>, images: List<ImageCache>)

    class Base(
        private val userDao: UserDao,
    ) : UserListLocalDataSource {
        override fun getUsers(): Flow<Result<List<UserAndImage>, Throwable>> =
            userDao.loadUsers().map { runOperationCatching { it } }


        override suspend fun saveUsers(users: List<UserCache>, images: List<ImageCache>) {
            userDao.insertUsersWithImages(users, images)
        }
    }
}