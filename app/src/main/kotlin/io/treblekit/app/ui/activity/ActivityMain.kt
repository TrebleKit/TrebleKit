package io.treblekit.app.ui.activity

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.treblekit.app.NormalFlutterActivity
import io.treblekit.app.ui.components.Background
import io.treblekit.app.ui.components.OverlayLayer
import io.treblekit.app.ui.destination.HomeDestination
import io.treblekit.app.ui.navigation.FlutterDestination
import io.treblekit.app.ui.navigation.HomeDestination
import io.treblekit.app.ui.theme.TrebleKitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityMain() {
    val navController = rememberNavController()
    Background { backdrop ->
        OverlayLayer(backdrop = backdrop) {
            NavHost(
                navController = navController,
                startDestination = HomeDestination,
            ) {
                composable<HomeDestination> {
                    HomeDestination(
                        navController = navController,
                        backdrop = backdrop,
                    )
                }
                activity<FlutterDestination> {
                    activityClass = NormalFlutterActivity::class
                }
            }
        }
    }
}

@Preview
@Composable
private fun ActivityMainPreview() {
    TrebleKitTheme {
        ActivityMain()
    }
}