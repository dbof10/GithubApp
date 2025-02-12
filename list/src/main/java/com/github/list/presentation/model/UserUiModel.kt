package com.github.list.presentation.model

import com.github.list.data.local.entity.LocalGithubUser
import com.github.list.data.remote.response.GitHubUser


data class UserUiModel(
    val login: String,
    val avatarUrl: String,
    val id: Int,
    val url: String,
    val htmlUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val gistsUrl: String,
    val starredUrl: String,
    val reposUrl: String,
)


fun LocalGithubUser.toUiModel(): UserUiModel {
    return UserUiModel(
        login = this.login,
        avatarUrl = this.avatarUrl,
        id = this.id,
        url = this.url,
        htmlUrl = this.htmlUrl,
        followersUrl = this.followersUrl,
        followingUrl = this.followingUrl,
        gistsUrl = this.gistsUrl,
        starredUrl = this.starredUrl,
        reposUrl = this.reposUrl
    )
}
