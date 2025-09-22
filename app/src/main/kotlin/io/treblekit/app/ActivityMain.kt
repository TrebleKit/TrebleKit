package io.treblekit.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import com.kyant.capsule.ContinuousCapsule
import com.kyant.capsule.ContinuousRoundedRectangle
import io.treblekit.app.ui.theme.CapsuleEdgePadding
import io.treblekit.app.ui.theme.CapsuleHeight
import io.treblekit.app.ui.theme.CapsuleIndent
import io.treblekit.app.ui.theme.CapsuleWidth
import io.treblekit.app.ui.theme.TrebleKitTheme
import kotlinx.serialization.Serializable

@Composable
fun ActivityMain(modifier: Modifier = Modifier) {
    var underLayerVisible: Boolean by remember { mutableStateOf(value = false) }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        UnderLayer(
            popBackStack = {
                underLayerVisible = false
            },
        )
        AnimatedVisibility(
            visible = !underLayerVisible, modifier = Modifier.fillMaxSize()
        ) {
            NavigationRoot(
                modifier = Modifier.fillMaxSize(),
                showUnderLayer = {
                    underLayerVisible = true
                },
            )
        }
    }
}

@Preview
@Composable
fun ActivityMainPreview() {
    TrebleKitTheme {
        ActivityMain()
    }
}

@Composable
fun UnderLayer(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit = NoOnClick,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ULTopBar()
        },
        bottomBar = {
            ULBottomBar(popBackStack = popBackStack)
        },
        containerColor = Color(color = 0xff1B1B2B),
    ) { innerPadding ->
EcosedPage(modifier = Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ULActionBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        TopAppBar(
            title = title,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            navigationIcon = navigationIcon,
            actions = actions,
            windowInsets = WindowInsets(),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ULTopBar(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        CenterAlignedTopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            title = {
                Text(
                    text = "Under",
                    color = Color.White,
                )
            },
            navigationIcon = {
                Box(
                    modifier = Modifier
                        .padding(start = CapsuleEdgePadding)
                        .width(width = CapsuleWidth)
                        .height(height = CapsuleHeight)
                        .clip(shape = ContinuousCapsule)
                        .background(color = Color(color = 0xff434056))
                        .clickable(onClick = {}),
                    contentAlignment = Alignment.Center,
                ) {
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            modifier = Modifier.size(size = 20.dp),
                            tint = Color(color = 0xff8E8E9E)
                        )
                        Text(
                            text = "搜索",
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 6.dp),
                            fontSize = 13.sp,
                            color = Color(color = 0xff8E8E9E),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            },
            actions = {
                Row(
                    modifier = Modifier
                        .padding(end = CapsuleEdgePadding)
                        .height(height = CapsuleHeight)
                        .width(width = CapsuleWidth)
                        .clip(shape = ContinuousCapsule)
                        .background(color = Color(color = 0xff434056)),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .weight(weight = 1f)
                            .fillMaxSize()
                            .clickable(onClick = NoOnClick),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreHoriz,
                            contentDescription = null,
                            tint = Color(color = 0xff8E8E9E),
                        )
                    }
                    VerticalDivider(
                        modifier = Modifier
                            .padding(vertical = CapsuleIndent)
                            .wrapContentWidth()
                            .fillMaxHeight(),
                        color = Color(color = 0xff8E8E9E),
                    )
                    Box(
                        modifier = Modifier
                            .weight(weight = 1f)
                            .fillMaxSize()
                            .clickable(onClick = NoOnClick),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                            tint = Color(color = 0xff8E8E9E),
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent,
            ),
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.5.dp,
            color = Color(color = 0x22000000)
        )
    }
}

@Composable
fun ULBottomBar(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit = NoOnClick,
) {
    BottomAppBar(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(
                shape = ContinuousRoundedRectangle(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                ),
            ),
        actions = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Left,
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(text = "返回")
                },
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                    )
                },
                onClick = popBackStack,
                modifier = Modifier.wrapContentSize(),
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            )
        },
        containerColor = Color(color = 0xff787493),
    )
}

@Composable
fun EcosedPage(
    modifier: Modifier = Modifier,
    backToApps: () -> Unit = NoOnClick,
    ) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        ULActionBar(
            modifier = Modifier.padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 8.dp,
            ),
            title = {
                Text(text = "EcosedKit")
            },
            navigationIcon = {
                IconButton(onClick = backToApps) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    top = 8.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                ),
            shape = ContinuousRoundedRectangle(size = 16.dp),
        ) {
            FlutterView(modifier = Modifier.fillMaxSize())
        }
    }

}

@Preview
@Composable
fun UnderLayerPreview() {
    TrebleKitTheme {
        UnderLayer()
    }
}

data class AppDestination<T>(
    val label: String,
    val route: T,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
)

@Serializable
data object Home

@Serializable
data object Settings

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    showUnderLayer: () -> Unit = NoOnClick,
) {
    val appDestination = arrayListOf(
        AppDestination(
            label = "Home",
            route = Home,
            icon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home,
        ),
        AppDestination(
            label = "Settings",
            route = Settings,
            icon = Icons.Outlined.Settings,
            selectedIcon = Icons.Filled.Settings,
        ),
    )
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val customNavSuiteType: NavigationSuiteType = with(
        receiver = currentWindowAdaptiveInfo(),
    ) {
        return@with when (windowSizeClass.windowWidthSizeClass) {
            WindowWidthSizeClass.COMPACT -> NavigationSuiteType.NavigationBar
            WindowWidthSizeClass.MEDIUM -> NavigationSuiteType.NavigationRail
            WindowWidthSizeClass.EXPANDED -> NavigationSuiteType.NavigationDrawer
            else -> NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
                adaptiveInfo = this@with
            )
        }
    }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            appDestination.forEach { destination ->
                val isCurrent: Boolean = currentDestination?.hierarchy?.any {
                    return@any it.hasRoute(route = destination.route::class)
                } == true
                item(
                    icon = {
                        Icon(
                            imageVector = if (isCurrent) {
                                destination.selectedIcon
                            } else {
                                destination.icon
                            },
                            contentDescription = destination.label,
                        )
                    },
                    modifier = Modifier.wrapContentSize(),
                    label = {
                        Text(text = destination.label)
                    },
                    selected = isCurrent,
                    onClick = {
                        navController.navigate(
                            route = destination.route,
                        ) {
                            popUpTo(
                                id = navController.graph.findStartDestination().id,
                            ) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    alwaysShowLabel = false,
                )
            }
        },
        modifier = modifier.fillMaxSize(),
        layoutType = customNavSuiteType,
    ) {
        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier.fillMaxSize(),
        ) {
            composable<Home> {
                HomeDestination(
                    showUnderLayer = showUnderLayer
                )
            }
            composable<Settings> {
//                SettingsDestination()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDestination(
    modifier: Modifier = Modifier,
    showUnderLayer: () -> Unit = NoOnClick,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = R.string.app_name,
                        ),
                    )
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = showUnderLayer,
            ) {
                Text("Under")
            }
        }
    }
}