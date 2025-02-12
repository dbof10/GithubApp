package com.github.detail.presentation

data class UserDetailState(
    val content: UserDetailUiModel?,
    val isLoading: Boolean,
    val error: Throwable?
)

data class UserDetailUiModel(
    val name: String, val follower: String, val following: String, val location: String?,
    val blog: String?,
    val avatarUrl: String
)
