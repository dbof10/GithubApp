package com.github.navigation.graph.list

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.github.detail.presentation.KEY_USER_NAME
import com.github.list.presentation.ListNavigator
import com.github.navigation.graph.Destinations
import com.github.navigation.graph.navigateTo

class ListNavigatorImpl(
    private val navController: NavController,
) : ListNavigator {


    override fun navigateToDetail(userName: String) {
        navController.navigateTo(
            route = Destinations.Detail.route,
            args = bundleOf(KEY_USER_NAME to userName),
        )
    }

}
