package com.github.navigation.graph


sealed class Destinations(open val route: String) {


    data object List : Destinations("list")

    data object Detail : Destinations("detail")

}
