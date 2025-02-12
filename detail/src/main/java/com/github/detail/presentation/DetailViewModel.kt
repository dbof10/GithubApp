package com.github.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.common.utils.DispatchersProvider
import com.github.detail.data.response.UserDetail
import com.github.detail.domain.GetUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val KEY_USER_NAME = "keyusername"

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val userName: String by lazy {
        savedStateHandle.get<String>(KEY_USER_NAME) ?: ""
    }

    private val _store = MutableStateFlow(UserDetailState(null, false, null))
    val store: StateFlow<UserDetailState> = _store


    fun load() {

        if (userName.isEmpty()) {
            _store.value = UserDetailState(null, false, RuntimeException("Cannot retrieve user id"))

        } else {
            viewModelScope.launch {

                _store.value = UserDetailState(null, true, null)
                try {
                    val user = withContext(dispatchersProvider.io) {
                        getUserDetailsUseCase.invoke(userName)
                    }
                    val userUiModel = toUserDetailUiModel(user)
                    _store.value = UserDetailState(userUiModel, false, null)

                } catch (e: Exception) {
                    _store.value = UserDetailState(null, false, e)
                }

            }
        }
    }

    private fun toUserDetailUiModel(userDetail: UserDetail): UserDetailUiModel {
        return UserDetailUiModel(
            avatarUrl = userDetail.avatarUrl,
            name = userDetail.name ?: "Unknown",
            follower = userDetail.followers.toString(),
            following = userDetail.following.toString(),
            location = userDetail.location,
            blog = if (userDetail.blog.isNullOrEmpty()) null else userDetail.blog
        )
    }
}
