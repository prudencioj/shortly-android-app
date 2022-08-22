package com.jprudencio.shortly.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jprudencio.shortly.ui.home.HomeRoute
import com.jprudencio.shortly.ui.ShortlyDestinations.HOME_ROUTE

@Composable
fun ShortlyNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME_ROUTE.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = HOME_ROUTE.route,
            arguments = HOME_ROUTE.arguments
        ) {
            HomeRoute(
                viewModel = hiltViewModel()
            )
        }
    }
}
