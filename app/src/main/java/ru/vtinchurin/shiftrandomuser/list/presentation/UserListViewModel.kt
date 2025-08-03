package ru.vtinchurin.shiftrandomuser.list.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.vtinchurin.shiftrandomuser.list.domain.repository.UserListRepository
import ru.vtinchurin.shiftrandomuser.utils.doOnError
import ru.vtinchurin.shiftrandomuser.utils.doOnSuccess

class UserListViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val userListRepository: UserListRepository,
) : ViewModel() {
    val state: StateFlow<UserListState> =
        savedStateHandle.getStateFlow(KEY, UserListState())

    init {
        userListRepository.getUsers()
            .onEach { result ->
                savedStateHandle[KEY] = state.value.copy(
                    isLoading = false,
                )
                result.doOnSuccess { list ->
                    savedStateHandle[KEY] = state.value.copy(
                        users = list.map { user -> user.toUi() },
                    )
                }
                result.doOnError {
                    savedStateHandle[KEY] = state.value.copy(error = it)
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }


    fun refresh() {
        savedStateHandle[KEY] = state.value.copy(isLoading = true)
        viewModelScope.launch {
            userListRepository.refreshUsers()
                .doOnError {
                    savedStateHandle[KEY] = state.value.copy(error = it, isLoading = false)
                    delay(2000)
                    savedStateHandle[KEY] = state.value.copy(error = null)
                }
        }
    }

    companion object {
        private const val KEY = "key_user_list"
    }
}