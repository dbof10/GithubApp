package com.github.list.presentation

sealed class ListResultsNavigation {
    data class OpenDetail(val username: String) : ListResultsNavigation()
}
