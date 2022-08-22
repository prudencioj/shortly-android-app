package com.jprudencio.shortly.ui

import androidx.navigation.NamedNavArgument

object ShortlyDestinations {
    val HOME_ROUTE = NavHomeRoute("home")
}

sealed class NavRoute(val route: String, val arguments: List<NamedNavArgument>)
class NavHomeRoute(route: String) : NavRoute(route, emptyList())