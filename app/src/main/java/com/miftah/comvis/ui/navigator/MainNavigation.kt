package com.miftah.comvis.ui.navigator

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.miftah.comvis.domain.FromScanned
import com.miftah.comvis.ui.add.AddingScreen
import com.miftah.comvis.ui.add.AddingViewModel
import com.miftah.comvis.ui.camera.CameraScreen
import com.miftah.comvis.ui.camera.CameraViewModel
import com.miftah.comvis.ui.home.HomeScreen
import com.miftah.comvis.ui.home.HomeViewModel
import com.miftah.comvis.ui.navgraph.Route
import com.miftah.comvis.utils.Constanta.SCANNED_IMAGE

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Route.HomeScreen.route) {
        composable(Route.HomeScreen.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(state = viewModel.state.value, toSpend = {
                navController.navigate(Route.SpadingScreen.route)
            }, toCamera = {
                navController.navigate(Route.CameraScreen.route)
            }, toHistory = {

            })
        }

        composable(
            Route.AddingScreen.route
        ) { backStackEntry ->
            val viewModel: AddingViewModel = hiltViewModel()
            var dataScan =
                navController.previousBackStackEntry?.savedStateHandle?.get<FromScanned>(
                    SCANNED_IMAGE
                )
            val context = LocalContext.current

            if (dataScan != null) {
                viewModel.inputImageAndValue(dataScan)
            } else {
                Toast.makeText(context, "ERR", Toast.LENGTH_SHORT).show()
            }

            AddingScreen(
                state = viewModel.state.value,
                event = viewModel::onEvent,
            ){
                navController.navigate(Route.HomeScreen.route)
            }
        }

        composable(Route.CameraScreen.route) {
            val viewModel: CameraViewModel = hiltViewModel()
            CameraScreen(state = viewModel.state.value, event = viewModel::onEvent) {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = SCANNED_IMAGE,
                    value = it
                )
                navController.navigate(Route.AddingScreen.route)
            }
        }

        composable(Route.SpadingScreen.route) {

        }
    }
}