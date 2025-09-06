package io.treblekit.app.ui.utils

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

fun <T : Any> navigateTo(
    navController: NavHostController? = null,
    route: T? = null,
): Boolean {
    if (navController != null && route != null) {
        navController.navigate(route = route) {
            popUpTo(
                id = navController.graph.findStartDestination().id,
            ) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        return true
    } else {
        return false
    }
}
