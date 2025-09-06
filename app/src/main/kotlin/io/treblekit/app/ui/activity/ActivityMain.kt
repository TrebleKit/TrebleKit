package io.treblekit.app.ui.activity

import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.treblekit.app.ui.components.IViewFactory
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
import io.treblekit.app.ui.theme.Background
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun ActivityMain(
    factory: IViewFactory? = null,
    useLiquidGlass: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TKTopBar(
                useLiquidGlass = useLiquidGlass,
            )
        },
        bottomBar = {
            TKNavBar(
                navController = navController,
                pages = PageList,
                startDestination = HomePage,
                background = Background,
                useLiquidGlass = useLiquidGlass,
            )
        },
        containerColor = Background,
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .padding(all = 16.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            NavHost(
                navController = navController,
                startDestination = HomePage,
                modifier = Modifier.fillMaxSize(),
                builder = {
                    composable<HomePage> {
                        HomePage(factory = factory)
                    }
                    composable<FeOSPage> {
                        FeOSPage(navController = navController, factory = factory)
                    }
                    composable<EKitPage> {
                        EKitPage(navController = navController)
                    }
                    composable<EbKitPage> {
                        EbKitPage()
                    }
                },
            )
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