package ru.vtinchurin.shiftrandomuser.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.vtinchurin.shiftrandomuser.detail.domain.repository.UserDetailRepository
import ru.vtinchurin.shiftrandomuser.utils.doOnError
import ru.vtinchurin.shiftrandomuser.utils.doOnSuccess

class UserDetailViewModel(
    private val userId: Long,
    savedStateHandle: SavedStateHandle,
    private val userDetailRepository: UserDetailRepository,
) : ViewModel() {

    val state: StateFlow<UserDetailScreenState> = savedStateHandle.getStateFlow(
        USER_ID_KEY, UserDetailScreenState()
    )

    init {
        viewModelScope.launch {
            userDetailRepository
                .loadUserDetail(userId)
                .doOnSuccess { userModel ->
                    savedStateHandle[USER_ID_KEY] = UserDetailScreenState(
                        user = UserUi(
                            name = userModel.name,
                            imageUrl = userModel.imageUrl,
                            city = userModel.city,
                            phone = userModel.phone,
                            email = userModel.email,
                            longitude = userModel.longitude,
                            latitude = userModel.latitude,
                        ),
                        isLoading = false,
                    )
                }.doOnError {
                    savedStateHandle[USER_ID_KEY] = UserDetailScreenState(
                        error = it,
                        isLoading = false,
                    )
                }
        }
    }

    companion object {
        private const val USER_ID_KEY = "user_id"
    }
}



