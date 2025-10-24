package io.treblekit.ui.activity

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.treblekit.app.NormalFlutterActivity
import io.treblekit.ui.components.Background
import io.treblekit.ui.components.OverlayLayer
import io.treblekit.ui.destination.HomeDestination
import io.treblekit.ui.navigation.FlutterDestination
import io.treblekit.ui.navigation.HomeDestination
import io.treblekit.ui.theme.TrebleKitTheme

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