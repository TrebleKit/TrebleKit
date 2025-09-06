package io.treblekit.app.ui.components

import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.treblekit.app.ui.navigation.EKitPage
import io.treblekit.app.ui.navigation.EbKitPage
import io.treblekit.app.ui.navigation.FeOSPage
import io.treblekit.app.ui.navigation.HomePage
import io.treblekit.app.ui.navigation.NavigationItem
import io.treblekit.app.ui.navigation.PageList
import io.treblekit.app.ui.theme.Background
import io.treblekit.app.ui.theme.TrebleKitTheme
import io.treblekit.app.ui.utils.navigateTo
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun <T : Any> TKScaffold(
    modifier: Modifier = Modifier,
    useMaterial: Boolean = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU,
    pages: ArrayList<NavigationItem<T>>,
    builder: NavGraphBuilder.(NavHostController) -> Unit,
) {
    val navController = rememberNavController()


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val targetPage = remember {
        mutableIntStateOf(value = 0)
    }
    LaunchedEffect(key1 = currentDestination) {
        snapshotFlow {
            currentDestination?.hierarchy
        }.collectLatest {
            for (page in pages) {
                val isCurrent: Boolean? = currentDestination?.hierarchy?.any {
                    return@any it.hasRoute(route = page.route::class)
                }
                if (isCurrent == true) {
                    pages.forEachIndexed { index, item ->
                        if (page.route == item.route) {
                            targetPage.intValue = index
                        }
                    }
                }
            }
        }
    }


    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TKTopBar(useMaterial = useMaterial)
        },
        bottomBar = {
            TKNavBar(
                background = Background,
                useMaterial = useMaterial,
                pages = pages,
                selectedIndexState = targetPage,
                onTabSelected = { index ->
                    navigateTo(
                        navController = navController,
                        route = pages[index].route,
                    )
                },
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
                startDestination = HomePage,
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
            useMaterial = false,
            pages = PageList,
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
            useMaterial = true,
            pages = PageList,
        ) {
            composable<HomePage> {}
            composable<FeOSPage> {}
            composable<EKitPage> {}
            composable<EbKitPage> {}
        }
    }
}