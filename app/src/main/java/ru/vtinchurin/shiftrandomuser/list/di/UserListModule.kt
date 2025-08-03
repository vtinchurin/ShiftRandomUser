package ru.vtinchurin.shiftrandomuser.list.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.vtinchurin.shiftrandomuser.core.network.UserRemoteDataSource
import ru.vtinchurin.shiftrandomuser.list.data.local.UserListLocalDataSource
import ru.vtinchurin.shiftrandomuser.list.data.repository.UserListRepositoryImpl
import ru.vtinchurin.shiftrandomuser.list.domain.repository.UserListRepository
import ru.vtinchurin.shiftrandomuser.list.presentation.UserListViewModel

val userListModule = module {

    single<UserListLocalDataSource> {
        UserListLocalDataSource.Base(
            userDao = get()
        )
    }

    single<UserRemoteDataSource> {
        UserRemoteDataSource.Base(
            service = get()
        )
    }
    single<UserListRepository> {
        UserListRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }

    viewModel {
        UserListViewModel(
            savedStateHandle = get(),
            userListRepository = get()
        )
    }
}