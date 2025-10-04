package io.treblekit.app.ui

import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.KeyboardCommandKey
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.KeyboardCommandKey
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
data object DashboardDestination

@Serializable
data object PlatformDestination

@Serializable
data object HomeDestination

@Serializable
data object FloatFlutterDestination

data class AppDestination<T>(
//    val label: Int,
    val route: T,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
)

val appDestination: ArrayList<AppDestination<out Any>> = arrayListOf(
    AppDestination(
        route = DashboardDestination,
        icon = Icons.Outlined.Dashboard,
        selectedIcon = Icons.Filled.Dashboard,
    ),
    AppDestination(
        route = PlatformDestination,
        icon = Icons.Outlined.KeyboardCommandKey,
        selectedIcon = Icons.Filled.KeyboardCommandKey,
    ),
)