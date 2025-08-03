package ru.vtinchurin.shiftrandomuser.list.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.vtinchurin.shiftrandomuser.core.local.entity.toUser
import ru.vtinchurin.shiftrandomuser.core.network.UserRemoteDataSource
import ru.vtinchurin.shiftrandomuser.core.network.model.toImageCache
import ru.vtinchurin.shiftrandomuser.core.network.model.toUserCache
import ru.vtinchurin.shiftrandomuser.list.data.local.UserListLocalDataSource
import ru.vtinchurin.shiftrandomuser.list.domain.model.User
import ru.vtinchurin.shiftrandomuser.list.domain.repository.UserListRepository
import ru.vtinchurin.shiftrandomuser.utils.Result
import ru.vtinchurin.shiftrandomuser.utils.doOnSuccess
import ru.vtinchurin.shiftrandomuser.utils.mapError
import ru.vtinchurin.shiftrandomuser.utils.mapSuccess

class UserListRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserListLocalDataSource,
) : UserListRepository {
    override suspend fun refreshUsers() = remoteDataSource.getUsers().doOnSuccess { users ->
        withContext(Dispatchers.IO) {
            val userCaches = async {
                users.mapIndexed { index, userInfo ->
                    userInfo.toUserCache(index.toLong())
                }
            }
            val imageCaches = async {
                users.mapIndexed { index, userInfo ->
                    userInfo.toImageCache(index.toLong())
                }
            }
            localDataSource.saveUsers(userCaches.await(), imageCaches.await())
        }
    }.mapError { throwable ->
        throwable.message ?: "No internet connection"
    }.mapSuccess { Unit }

    override fun getUsers(): Flow<Result<List<User>, String>> {
        return localDataSource.getUsers().map { result ->
            result
                .mapSuccess { usersDao ->
                    usersDao.map { it.toUser() }
                }.mapError { throwable ->
                    throwable.message ?: "Internal error"
                }
        }
    }
}