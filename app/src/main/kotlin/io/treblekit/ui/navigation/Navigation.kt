package io.treblekit.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.ui.graphics.vector.ImageVector
import io.treblekit.R
import kotlinx.serialization.Serializable

@Serializable
data object DashboardDestination

@Serializable
data object PlatformDestination

data class AppDestination<T>(
    val route: T,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val label: Int,
)

val appDestination: ArrayList<AppDestination<out Any>> = arrayListOf(
    AppDestination(
        route = DashboardDestination,
        icon = Icons.Outlined.Dashboard,
        selectedIcon = Icons.Filled.Dashboard,
        label = R.string.destination_dashboard,
    ),
    AppDestination(
        route = PlatformDestination,
        icon = Icons.Outlined.Memory,
        selectedIcon = Icons.Filled.Memory,
        label = R.string.destination_platform,
    ),
)