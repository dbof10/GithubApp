package com.github.navigation.graph.detail

import androidx.navigation.NavController
import com.github.detail.presentation.DetailNavigator

class DetailNavigatorImpl(private val navController: NavController) : DetailNavigator {
    override fun back() {
        navController.popBackStack()
    }
}
