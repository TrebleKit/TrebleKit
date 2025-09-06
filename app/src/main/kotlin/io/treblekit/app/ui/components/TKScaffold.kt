package io.treblekit.app.ui.components

import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.treblekit.app.ui.navigation.EKitPage
import io.treblekit.app.ui.navigation.EbKitPage
import io.treblekit.app.ui.navigation.FeOSPage
import io.treblekit.app.ui.navigation.HomePage
import io.treblekit.app.ui.navigation.NavigationItem
import io.treblekit.app.ui.navigation.PageList
import io.treblekit.app.ui.theme.Background
import io.treblekit.app.ui.theme.TrebleKitTheme
import kotlinx.coroutines.FlowPreview

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun <T : Any> TKScaffold(
    modifier: Modifier = Modifier,
    pages: ArrayList<NavigationItem<T>>,
    startDestination: T,
    useLiquidGlass: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU,
    builder: NavGraphBuilder.(NavHostController) -> Unit,
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TKTopBar(useLiquidGlass = useLiquidGlass)
        },
        bottomBar = {
            TKNavBar(
                background = Background,
                useMaterial = !useLiquidGlass,
                pages = pages,
                startDestination = startDestination,
                navController = navController,
            )
        },
        containerColor = Background,
    ) { innerPadding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .padding(all = 16.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.fillMaxSize(),
                builder = { builder(navController) },
            )
        }
    }
}

@Preview
@Composable
private fun TKScaffoldLiquidGlassPreview() {
    TrebleKitTheme {
        TKScaffold(
            useLiquidGlass = true,
            pages = PageList,
            startDestination = HomePage,
        ) {
            composable<HomePage> {}
            composable<FeOSPage> {}
            composable<EKitPage> {}
            composable<EbKitPage> {}
        }
    }
}

@Preview
@Composable
private fun TKScaffoldMaterialPreview() {
    TrebleKitTheme {
        TKScaffold(
            useLiquidGlass = false,
            pages = PageList,
            startDestination = HomePage,
        ) {
            composable<HomePage> {}
            composable<FeOSPage> {}
            composable<EKitPage> {}
            composable<EbKitPage> {}
        }
    }
}