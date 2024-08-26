package com.miftah.comvis.ui.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.miftah.comvis.ui.navigator.MainNavigation

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    currentRoute : String,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = currentRoute) {
        navigation(route = Route.MainNavigator.route, startDestination = Route.EmptyGraph.route){
            composable(route = Route.EmptyGraph.route) {
                MainNavigation()
            }
        }
    }
}