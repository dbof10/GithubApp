package com.github.navigation.graph

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import timber.log.Timber

@SuppressLint("RestrictedApi")
fun NavController.navigateTo(
    route: String,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    val nodeId = graph.findNode(route)?.id
    try {
        if (nodeId != null) {
            navigate(nodeId, args, navOptions, navigatorExtras)
        } else {
            // this case route did not add to navHost
            navigate(route, navOptions, navigatorExtras)
        }
    } catch (ex: Exception) {
        val backStackRoutes = this.currentBackStack.value.map {
            it.destination.route
        }
        Timber.e(
            ex,
            "destination: $route, " +
                "backStack: ${backStackRoutes.joinToString(",")}, " +
                "args: $args, navOptions: $navOptions, navigatorExtras: $navigatorExtras",
        )
    }
}
