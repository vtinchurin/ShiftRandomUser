package ru.vtinchurin.shiftrandomuser.detail.data.repository

import ru.vtinchurin.shiftrandomuser.core.local.entity.toUserDetail
import ru.vtinchurin.shiftrandomuser.detail.data.local.UserDetailLocalDataSource
import ru.vtinchurin.shiftrandomuser.detail.domain.model.UserDetail
import ru.vtinchurin.shiftrandomuser.detail.domain.repository.UserDetailRepository
import ru.vtinchurin.shiftrandomuser.utils.Result
import ru.vtinchurin.shiftrandomuser.utils.mapError
import ru.vtinchurin.shiftrandomuser.utils.mapSuccess

class UserDetailRepositoryImpl(
    private val localDataSource: UserDetailLocalDataSource,
) : UserDetailRepository {
    override suspend fun loadUserDetail(id: Long): Result<UserDetail, String> =
        localDataSource.getUserDetail(id)
            .mapSuccess { userDto -> userDto.toUserDetail() }
            .mapError { it.message ?: "User detail not found" }
}