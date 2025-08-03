package ru.vtinchurin.shiftrandomuser.detail.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.vtinchurin.shiftrandomuser.detail.data.local.UserDetailLocalDataSource
import ru.vtinchurin.shiftrandomuser.detail.data.repository.UserDetailRepositoryImpl
import ru.vtinchurin.shiftrandomuser.detail.domain.repository.UserDetailRepository
import ru.vtinchurin.shiftrandomuser.detail.presentation.UserDetailViewModel

val detailModule = module {

    factory<UserDetailLocalDataSource> {
        UserDetailLocalDataSource.Base(
            userDao = get()
        )
    }

    factory<UserDetailRepository> {
        UserDetailRepositoryImpl(
            localDataSource = get(),
        )
    }

    viewModel { (userId: Long) ->
        UserDetailViewModel(
            userId = userId,
            savedStateHandle = get(),
            userDetailRepository = get(),
        )
    }
}