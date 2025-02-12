package com.github.navigation.graph.list

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.github.list.presentation.UsersScreen
import com.github.navigation.graph.Destinations

fun NavGraphBuilder.list(navController: NavController) {
    composable(Destinations.List.route) {
        UsersScreen(
            navigator = remember(navController) {
                ListNavigatorImpl(navController)
            },
        )
    }
}
