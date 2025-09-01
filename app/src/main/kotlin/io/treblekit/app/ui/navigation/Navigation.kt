package io.treblekit.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.CompareArrows
import androidx.compose.material.icons.twotone.Dashboard
import androidx.compose.material.icons.twotone.KeyboardCommandKey
import androidx.compose.material.icons.twotone.Memory
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

data class NavigationItem<T>(
    val page: Int,
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
        page = 0,
        route = HomePage,
        label = "主页",
        icon = Icons.TwoTone.Dashboard,
    ),
    NavigationItem(
        page = 1,
        route = FeOSPage,
        label = "feOS",
        icon = Icons.TwoTone.Memory,
    ),
    NavigationItem(
        page = 2,
        route = EKitPage,
        label = "EKit",
        icon = Icons.TwoTone.KeyboardCommandKey,
    ),
    NavigationItem(
        page = 3,
        route = EbKitPage,
        label = "EbKit",
        icon = Icons.AutoMirrored.TwoTone.CompareArrows,
    ),
)