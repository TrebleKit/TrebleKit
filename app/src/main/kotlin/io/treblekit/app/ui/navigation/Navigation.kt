package io.treblekit.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.CompareArrows
import androidx.compose.material.icons.twotone.Dashboard
import androidx.compose.material.icons.twotone.KeyboardCommandKey
import androidx.compose.material.icons.twotone.Memory
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
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

@Composable
fun rememberNavGraph(
    navController: NavHostController,
    home: @Composable (NavHostController) -> Unit,
    feOS: @Composable (NavHostController) -> Unit,
    eKit: @Composable (NavHostController) -> Unit,
    ebKit: @Composable (NavHostController) -> Unit,
): NavGraphBuilder.() -> Unit {
    return {
        composable<HomePage> {
            home(navController)
        }
        composable<FeOSPage> {
            feOS(navController)
        }
        composable<EKitPage> {
            eKit(navController)
        }
        composable<EbKitPage> {
            ebKit(navController)
        }
    }
}