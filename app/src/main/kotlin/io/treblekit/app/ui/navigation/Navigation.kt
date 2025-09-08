package io.treblekit.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.CompareArrows
import androidx.compose.material.icons.twotone.Dashboard
import androidx.compose.material.icons.twotone.KeyboardCommandKey
import androidx.compose.material.icons.twotone.Memory
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

data class NavigationItem<T>(
    val route: T,
    val label: String,
    val icon: ImageVector,
)

@Serializable
data object HomePage

@Serializable
data object FeOSPage

@Serializable
data object EKitPage

@Serializable
data object EbKitPage

val PageList: ArrayList<NavigationItem<Any>> = arrayListOf(
    NavigationItem(
        route = HomePage,
        label = "主页",
        icon = Icons.TwoTone.Dashboard,
    ),
    NavigationItem(
        route = FeOSPage,
        label = "feOS",
        icon = Icons.TwoTone.Memory,
    ),
    NavigationItem(
        route = EKitPage,
        label = "EKit",
        icon = Icons.TwoTone.KeyboardCommandKey,
    ),
    NavigationItem(
        route = EbKitPage,
        label = "EbKit",
        icon = Icons.AutoMirrored.TwoTone.CompareArrows,
    ),
)