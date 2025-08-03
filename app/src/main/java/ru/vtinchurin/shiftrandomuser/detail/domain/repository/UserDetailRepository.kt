package ru.vtinchurin.shiftrandomuser.detail.domain.repository

import ru.vtinchurin.shiftrandomuser.detail.domain.model.UserDetail
import ru.vtinchurin.shiftrandomuser.utils.Result

typealias ErrorMessage = String

interface UserDetailRepository {

    suspend fun loadUserDetail(id: Long): Result<UserDetail, ErrorMessage>
}