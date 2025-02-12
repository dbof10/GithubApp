package com.github.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.github.common.theme.GithubTheme
import com.github.navigation.graph.Destinations
import com.github.navigation.graph.detail.details
import com.github.navigation.graph.list.list
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            GithubTheme {
                NavHost(
                    navController = navController,
                    startDestination = Destinations.List.route,
                ) {
                    list(navController)
                    details(navController)
                }
            }
        }
    }
}
