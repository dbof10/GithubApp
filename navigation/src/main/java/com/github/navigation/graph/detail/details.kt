package com.github.navigation.graph.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.github.detail.presentation.UserDetailsScreen
import com.github.navigation.graph.Destinations

fun NavGraphBuilder.details(navController: NavController) {
    composable(route = Destinations.Detail.route) {
        UserDetailsScreen(DetailNavigatorImpl(navController))
    }
}
