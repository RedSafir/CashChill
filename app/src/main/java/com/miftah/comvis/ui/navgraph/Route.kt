package com.miftah.comvis.ui.navgraph

import com.miftah.comvis.utils.Constanta.SCANNED_IMAGE

sealed class Route(
    val route : String
) {
    data object EmptyGraph : Route("empty_graph")

    data object OnBoardingNavigator : Route("onboarding_navigator")

    data object MainNavigator : Route("main_navigator")

    data object HomeScreen : Route("home_screen")
    data object AddingScreen : Route("adding_screen/{$SCANNED_IMAGE}")
    data object SpadingScreen : Route("spading_screen")
    data object CameraScreen : Route("camera_screen")
}