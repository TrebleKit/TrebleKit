package io.treblekit.app.ui

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlutterDash
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.twotone.Dashboard
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.KeyboardCommandKey
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
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
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.imageloading.rememberDrawablePainter
import com.kyant.capsule.ContinuousCapsule
import com.kyant.capsule.ContinuousRoundedRectangle
import io.treblekit.app.R
import io.treblekit.app.hybrid.FlutterView
import io.treblekit.app.ui.theme.CapsuleEdgePadding
import io.treblekit.app.ui.theme.CapsuleHeight
import io.treblekit.app.ui.theme.CapsuleIndent
import io.treblekit.app.ui.theme.CapsuleWidth
import io.treblekit.app.ui.theme.TrebleKitTheme
import io.treblekit.app.ui.utils.NoOnClick
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Composable
fun ActivityMain() {
    TrebleKitTheme {
        UnderLayer(
            popBackStack = {},
        )
    }
}

@Preview
@Composable
private fun ActivityMainPreview() {
    ActivityMain()
}



@Serializable
data object DashboardPage

@Serializable
data object EcosedKitPage

private data class MPPage<T>(
    val page: Int,
    val route: T,
)

private val pages = arrayListOf(
    MPPage(
        page = 0,
        route = DashboardPage,
    ),
    MPPage(
        page = 1,
        route = EcosedKitPage,
    ),
)

@Composable
fun UnderLayer(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit = NoOnClick,
    initialPage: Int = getPageWithRoute(
        route = DashboardPage,
    ),
) {
    val pageState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { pages.size },
    )
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ULTopBar()
        },
        bottomBar = {
            ULBottomBar(
                popBackStack = popBackStack,
                animateToApps = {
                    coroutineScope.launch {
                        pageState.animateScrollToPage(
                            page = getPageWithRoute(
                                route = DashboardPage,
                            ),
                        )
                    }
                },
                animateToEcosed = {
                    coroutineScope.launch {
                        pageState.animateScrollToPage(
                            page = getPageWithRoute(
                                route = EcosedKitPage,
                            ),
                        )
                    }
                },
            )
        },
        containerColor = Color(color = 0xff1B1B2B),
    ) { innerPadding ->
        HorizontalPager(
            state = pageState,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    paddingValues = innerPadding,
                ),
            userScrollEnabled = false,
        ) { page ->
            when (pages[page].route) {
                DashboardPage -> ULAppsPage(
                    popBackStack = popBackStack,
                    animateToEcosed = {
                        coroutineScope.launch {
                            pageState.animateScrollToPage(
                                page = getPageWithRoute(
                                    route = EcosedKitPage,
                                ),
                            )
                        }
                    },
                )

                EcosedKitPage -> ULEKitPage(
                    animateToApps = {
                        coroutineScope.launch {
                            pageState.animateScrollToPage(
                                page = getPageWithRoute(
                                    route = DashboardPage,
                                ),
                            )
                        }
                    },
                )

                else -> Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "未知页面",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun UnderLayerAppsPreview() {
    TrebleKitTheme {
        UnderLayer(
            initialPage = getPageWithRoute(
                route = DashboardPage,
            ),
        )
    }
}

@Preview
@Composable
fun UnderLayerEKitPreview() {
    TrebleKitTheme {
        UnderLayer(
            initialPage = getPageWithRoute(
                route = EcosedKitPage,
            ),
        )
    }
}


private fun <T> getPageWithRoute(route: T): Int {
    var result = 0
    for (page in pages) {
        if (page.route == route) {
            result = page.page
        }
    }
    return result
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ULActionBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 56.dp),
        shape = ContinuousRoundedRectangle(size = 16.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        TopAppBar(
            title = title,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            navigationIcon = navigationIcon,
            actions = {
                IconButton(onClick = NoOnClick) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = null,
                    )
                }
            },
            windowInsets = WindowInsets(),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ULTopBar(
    modifier: Modifier = Modifier,
) {
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
                    text = stringResource(id = R.string.app_name),
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
            color = Color.Black.copy(
                alpha = 0.2f
            )
        )
    }
}

@Composable
fun ULBottomBar(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit = NoOnClick,
    animateToApps: () -> Unit = NoOnClick,
    animateToEcosed: () -> Unit = NoOnClick,
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
        containerColor = Color(color = 0xff787493),
        actions = {
            IconButton(
                onClick = {

                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Android,
                    contentDescription = null,
                    tint = Color(color = 0xFF3EDC87)
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "Android",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Left,
            )
        },
        floatingActionButton = {
            Row {
                ULNavBlock(
                    animateToApps = animateToApps,
                    animateToEcosed = animateToEcosed,
                )
                ULPopFAB(
                    popBackStack = popBackStack,
                )
            }
        }
    )
}

@Composable
fun ULNavBlock(
    modifier: Modifier = Modifier,
    animateToApps: () -> Unit = NoOnClick,
    animateToEcosed: () -> Unit = NoOnClick,
) {
    Surface(
        modifier = modifier
            .wrapContentSize()
            .sizeIn(minWidth = 80.dp)
            .padding(end = 4.dp),
        shape = ContinuousRoundedRectangle(size = 16.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .defaultMinSize(minHeight = 56.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = animateToApps,
                modifier = Modifier.wrapContentSize(),
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Dashboard,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            IconButton(
                onClick = animateToEcosed,
                modifier = Modifier.wrapContentSize(),
            ) {
                Icon(
                    imageVector = Icons.TwoTone.KeyboardCommandKey,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    }
}

@Composable
fun ULPopFAB(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit = NoOnClick,
) {
    ExtendedFloatingActionButton(
        text = {
            Text(text = "返回")
        },
        icon = {
            Icon(
                imageVector = Icons.TwoTone.Home,
                contentDescription = null,
            )
        },
        onClick = popBackStack,
        modifier = modifier
            .wrapContentSize()
            .padding(start = 4.dp),
        shape = ContinuousRoundedRectangle(size = 16.dp),
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
    )
}

@Composable
fun ULAppsPage(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit = NoOnClick,
    animateToEcosed: () -> Unit = NoOnClick,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState),
    ) {
        Text(
            text = "核心服务",
            modifier = Modifier.padding(
                start = 30.dp,
                top = 30.dp,
                end = 30.dp,
                bottom = 15.dp,
            ),
            fontSize = 14.sp,
            color = Color(
                color = 0xFF8E8E9E,
            ),
        )
        MPPlayer(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            popBackStack = popBackStack,
            animateToFlutter = animateToEcosed,
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    start = 16.dp, top = 15.dp, end = 16.dp, bottom = 15.dp
                ),
            shape = ContinuousRoundedRectangle(size = 16.dp),
            color = Color(color = 0xFF434056)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Row {
                    Text(
                        text = "互联互通",
                        fontSize = 14.sp,
                        color = Color(
                            color = 0xFF8E8E9E,
                        ),
                    )
                }
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
                ) {
                    Column(
                        modifier = modifier.wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(size = 40.dp)
                                .clip(shape = RoundedCornerShape(size = 35.dp))
                                .background(color = Color(color = 0xFF8E8E9E))
                                .clickable(onClick = {}),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null,
                            )
                        }
                        Text(
                            text = "app",
                            fontSize = 15.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Column(
                        modifier = modifier.wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(size = 40.dp)
                                .clip(shape = RoundedCornerShape(size = 35.dp))
                                .background(color = Color(color = 0xFF8E8E9E))
                                .clickable(onClick = {}),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null,
                            )
                        }
                        Text(
                            text = "app",
                            fontSize = 15.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Column(
                        modifier = modifier.wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(size = 40.dp)
                                .clip(shape = RoundedCornerShape(size = 35.dp))
                                .background(color = Color(color = 0xFF8E8E9E))
                                .clickable(onClick = {}),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null,
                            )
                        }
                        Text(
                            text = "app",
                            fontSize = 15.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    }


                }
            }
        }
        Text(
            text = "常用应用",
            modifier = Modifier.padding(
                start = 30.dp,
                top = 30.dp,
                end = 30.dp,
                bottom = 15.dp,
            ),
            fontSize = 14.sp,
            color = Color(
                color = 0xFF8E8E9E,
            ),
        )
        AppsGrid(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
            ),
            list = miniProgramList,
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MPPlayer(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit = NoOnClick,
    animateToFlutter: () -> Unit = NoOnClick,
) {
    val context: Context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 90.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(weight = 1f),
        ) {
            AppItem(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxSize(),
                onLaunch = popBackStack,
                style = AppItemStyle.Image,
                appIcon = rememberDrawablePainter(
                    drawable = AppCompatResources.getDrawable(
                        context,
                        R.mipmap.ic_launcher,
                    ),
                ),
                appName = "TrebleKit",
            )
            AppItem(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxSize(),
                onLaunch = {},
                style = AppItemStyle.Image,
                appIcon = rememberDrawablePainter(
                    drawable = AppCompatResources.getDrawable(
                        context,
                        R.mipmap.ic_launcher,
                    ),
                ),
                appName = "EbKit",
            )
        }
        RecentPlayer(
            modifier = Modifier
                .weight(weight = 1f)
                .padding(start = 16.dp)
                .fillMaxSize(),
            animateToFlutter = animateToFlutter,
        )
    }
}

@Preview
@Composable
fun MPPlayerPreview() {
    TrebleKitTheme {
        MPPlayer()
    }
}

@Composable
fun RecentPlayer(
    modifier: Modifier = Modifier,
    animateToFlutter: () -> Unit = NoOnClick,
) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .height(height = 60.dp)
                .fillMaxWidth()
                .clip(shape = ContinuousCapsule)
                .background(Color(color = 0xFF434056))
                .clickable(onClick = animateToFlutter),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.FlutterDash,
                    contentDescription = null,
                    modifier = Modifier.size(size = 30.dp),
                    tint = Color(color = 0xFF8E8E9E)
                )
                Text(
                    text = "暂无内容",
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 10.dp),
                    fontSize = 16.sp,
                    color = Color(color = 0xFF8E8E9E),
                    textAlign = TextAlign.Center,
                )
            }
        }
        Text(
            text = "Ecosed",
            fontSize = 15.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun RecentPlayerPreview() {
    TrebleKitTheme {
        RecentPlayer()
    }
}

data class MiniProgramItem(
    /** 名称 */
    val title: String,
    /** 图标 */
    val icon: String,
)

@OptIn(ExperimentalCoilApi::class)
@Composable
fun AppsGrid(
    modifier: Modifier = Modifier,
    list: ArrayList<MiniProgramItem>,
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 180.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
        verticalArrangement = Arrangement.spacedBy(space = 10.dp),
        columns = GridCells.Fixed(count = 4),
        userScrollEnabled = false,
    ) {
        items(items = list) { item ->
            Box {
                AppItem(
                    style = AppItemStyle.Image,
                    appIcon = rememberImagePainter(data = item.icon),
                    appName = item.title,
                )
            }
        }
    }
}

val miniProgramList: ArrayList<MiniProgramItem> = arrayListOf(
    MiniProgramItem(
        title = "饿了么",
        icon = "https://img0.baidu.com/it/u=2625005847,2716895016&fm=253&fmt=auto&app=138&f=JPEG?w=200&h=200"
    ),
    MiniProgramItem(
        title = "美图",
        icon = "https://img1.baidu.com/it/u=1752963805,4078506746&fm=253&fmt=auto&app=138&f=JPEG?w=200&h=200"
    ),
    MiniProgramItem(
        title = "滴滴",
        icon = "https://img0.baidu.com/it/u=1068101613,1323308017&fm=253&fmt=auto&app=138&f=PNG?w=200&h=200"
    ),
    MiniProgramItem(
        title = "青橘单车",
        icon = "https://img0.baidu.com/it/u=195120191,2939897897&fm=253&fmt=auto&app=138&f=PNG?w=190&h=190"
    ),
    MiniProgramItem(
        title = "斗地主",
        icon = "https://img2.baidu.com/it/u=926635057,1451495262&fm=253&fmt=auto&app=138&f=PNG?w=190&h=190"
    ),
    MiniProgramItem(
        title = "羊城通",
        icon = "https://img2.baidu.com/it/u=2751300851,4181594410&fm=253&fmt=auto&app=138&f=JPEG?w=200&h=200"
    ),
    MiniProgramItem(
        title = "美图秀秀",
        icon = "https://img1.baidu.com/it/u=417359459,147216874&fm=253&fmt=auto&app=138&f=PNG?w=200&h=200"
    ),
    MiniProgramItem(
        title = "拼多多",
        icon = "https://img2.baidu.com/it/u=620052409,134315960&fm=253&fmt=auto&app=138&f=PNG?w=190&h=190"
    ),
)

enum class AppItemStyle {
    Image, Icon,
}

@Composable
fun AppItem(
    modifier: Modifier = Modifier,
    onLaunch: () -> Unit = NoOnClick,
    style: AppItemStyle,
    appIcon: Painter,
    appName: String,
) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(size = 60.dp)
                .clip(shape = RoundedCornerShape(size = 35.dp))
                .background(color = Color(color = 0xff434056))
                .clickable(onClick = onLaunch),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = appIcon,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = when (style) {
                    AppItemStyle.Image -> Modifier
                        .fillMaxSize()
                        .clip(
                            shape = RoundedCornerShape(
                                size = 35.dp,
                            ),
                        )

                    AppItemStyle.Icon -> Modifier.size(
                        size = 30.dp,
                    )
                }
            )
        }
        Text(
            text = appName,
            fontSize = 15.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun AppItemPreview() {
    TrebleKitTheme {
        AppItem(
            style = AppItemStyle.Icon,
            appIcon = painterResource(id = R.drawable.baseline_preview_24),
            appName = "Preview",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ULEKitPage(
    modifier: Modifier = Modifier,
    animateToApps: () -> Unit = NoOnClick,
) {
    val inspection: Boolean = LocalInspectionMode.current
    val inspectionModeText: String = stringResource(
        id = R.string.inspection_mode_text,
    )
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
                IconButton(
                    onClick = animateToApps,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                    )
                }
            },
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
            if (!inspection) {
                FlutterView(
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                Scaffold(
                    contentWindowInsets = WindowInsets(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text("EcosedKit")
                            },
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = inspectionModeText)
                    }
                }
            }
        }
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

@Preview
@Composable
fun HomeDestinationPreview() {
    TrebleKitTheme {
        HomeDestination()
    }
}