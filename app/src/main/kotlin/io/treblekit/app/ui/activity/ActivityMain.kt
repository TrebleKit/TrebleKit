package io.treblekit.app.ui.activity

import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.treblekit.app.ui.components.TKContent
import io.treblekit.app.ui.components.TKNavBar
import io.treblekit.app.ui.components.TKTopBar
import io.treblekit.app.ui.navigation.EKitPage
import io.treblekit.app.ui.navigation.EbKitPage
import io.treblekit.app.ui.navigation.FeOSPage
import io.treblekit.app.ui.navigation.HomePage
import io.treblekit.app.ui.navigation.PageList
import io.treblekit.app.ui.page.EKitPage
import io.treblekit.app.ui.page.EbKitPage
import io.treblekit.app.ui.page.FeOSPage
import io.treblekit.app.ui.page.HomePage
import io.treblekit.app.ui.theme.AppBackgroundColor
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun ActivityMain(
    modifier: Modifier = Modifier,
    useLiquidGlass: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU,
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TKTopBar(
                useLiquidGlass = useLiquidGlass,
            )
        },
        bottomBar = {
            TKNavBar(
                navController = navController,
                pages = PageList,
                useLiquidGlass = useLiquidGlass,
            )
        },
        containerColor = AppBackgroundColor,
    ) { innerPadding ->
        TKContent(
            modifier = Modifier.padding(
                paddingValues = innerPadding,
            ),
        ) {
            NavHost(
                navController = navController,
                startDestination = FeOSPage,
                modifier = Modifier.fillMaxSize(),
            ) {
                composable<HomePage> {
                    HomePage(navController = navController)
                }
                composable<FeOSPage> {
                    FeOSPage(navController = navController)
                }
                composable<EKitPage> {
                    EKitPage(navController = navController)
                }
                composable<EbKitPage> {
                    EbKitPage(navController = navController)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ActivityMainLiquidGlassPreview() {
    TrebleKitTheme {
        ActivityMain(
            useLiquidGlass = true,
        )
    }
}

@Preview
@Composable
private fun ActivityMainNoLiquidGlassPreview() {
    TrebleKitTheme {
        ActivityMain(
            useLiquidGlass = false,
        )
    }
}