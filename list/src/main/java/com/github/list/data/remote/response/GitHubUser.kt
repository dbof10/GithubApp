package com.github.list.data.remote.response

import com.squareup.moshi.Json


data class GitHubUser(
    @Json(name = "login") val login: String,
    @Json(name = "id") val id: Int,
    @Json(name = "node_id") val nodeId: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "gravatar_id") val gravatarId: String,
    @Json(name = "url") val url: String,
    @Json(name = "html_url") val htmlUrl: String,
    @Json(name = "followers_url") val followersUrl: String,
    @Json(name = "following_url") val followingUrl: String,
    @Json(name = "gists_url") val gistsUrl: String,
    @Json(name = "starred_url") val starredUrl: String,
    @Json(name = "subscriptions_url") val subscriptionsUrl: String,
    @Json(name = "organizations_url") val organizationsUrl: String,
    @Json(name = "repos_url") val reposUrl: String,
    @Json(name = "events_url") val eventsUrl: String,
    @Json(name = "received_events_url") val receivedEventsUrl: String,
    @Json(name = "type") val type: String,
    @Json(name = "user_view_type") val userViewType: String,
    @Json(name = "site_admin") val siteAdmin: Boolean
)
