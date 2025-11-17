package io.treblekit.ui.destination

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.twotone.Category
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.capsule.ContinuousCapsule
import com.kyant.capsule.ContinuousRoundedRectangle
import io.treblekit.resources.ResLogo
import io.treblekit.ui.components.AppItem
import io.treblekit.ui.navigation.PlatformDestination
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.utils.NoOnClick
import io.treblekit.ui.utils.backdropEffects
import io.treblekit.ui.utils.navigateToRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DashboardDestination(
    modifier: Modifier = Modifier,
    pageState: PagerState? = null,
    backdrop: LayerBackdrop = rememberLayerBackdrop(),
    popBackStack: () -> Unit = NoOnClick,
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
            color = MaterialTheme.colorScheme.onBackground,
        )
        MPPlayer(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            pageState = pageState,
            backdrop = backdrop,
            popBackStack = popBackStack,
        )
        ControlCard(
            modifier = Modifier.padding(
                start = 16.dp,
                top = 15.dp,
                end = 16.dp,
                bottom = 15.dp,
            ),
            backdrop = backdrop,
        )
        Text(
            text = "常用应用",
            modifier = Modifier.padding(
                start = 30.dp,
                top = 30.dp,
                end = 30.dp,
                bottom = 15.dp,
            ),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )
        AppsGrid(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
            ),
            backdrop = backdrop,
            list = miniProgramList,
        )
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun DashboardDestinationPreview() {
    TrebleKitTheme {
        DashboardDestination()
    }
}

@Composable
fun MPPlayer(
    modifier: Modifier = Modifier,
    pageState: PagerState? = null,
    backdrop: LayerBackdrop = rememberLayerBackdrop(),
    popBackStack: () -> Unit = NoOnClick,
) {
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
                backdrop = backdrop,
                onLaunch = popBackStack,
                appIcon = painterResource(
                    id = ResLogo.TREBLEKIT_LOGO,
                ),
                appName = "TrebleKit",
            )
            AppItem(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxSize(),
                backdrop = backdrop,
                onLaunch = {},
                appIcon = painterResource(
                    id = ResLogo.EBKIT_LOGO,
                ),
                appName = "EbKit",
            )
        }
        RecentPlayer(
            modifier = Modifier
                .weight(weight = 1f)
                .padding(start = 16.dp)
                .fillMaxSize(),
            pageState = pageState,
            backdrop = backdrop,
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
    pageState: PagerState? = null,
    backdrop: LayerBackdrop = rememberLayerBackdrop(),
) {
    val primaryContainerTint: Color = MaterialTheme.colorScheme.primaryContainer
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 60.dp)
                .backdropEffects(
                    backdrop = backdrop,
                    shape = ContinuousCapsule,
                    color = primaryContainerTint,
                    alpha = 0.8f,
                    bigBlock = false,
                )
                .clickable {
                    coroutineScope.launch {
                        pageState.navigateToRoute(route = PlatformDestination)
                    }
                },
            contentAlignment = Alignment.Center,
        ) {
            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Category,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(size = 30.dp),
                )
                Text(
                    text = "软件平台",
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 10.dp),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center,
                )
            }
        }
        Text(
            text = "Treble",
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

@Composable
fun ControlCard(
    modifier: Modifier = Modifier,
    backdrop: LayerBackdrop = rememberLayerBackdrop(),
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .backdropEffects(
                backdrop = backdrop,
                shape = ContinuousRoundedRectangle(size = 16.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                alpha = 0.8f,
                bigBlock = true,
            ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row {
                Text(
                    text = "互联互通",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
            ) {
                Column(
                    modifier = Modifier.wrapContentSize(),
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
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center,
                    )
                }
                Column(
                    modifier = Modifier.wrapContentSize(),
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
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center,
                    )
                }
                Column(
                    modifier = Modifier.wrapContentSize(),
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
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ControlCardPreview() {
    TrebleKitTheme {
        ControlCard()
    }
}

data class MiniProgramItem(
    /** 名称 */
    val title: String,
    /** 图标 */
    val icon: Int,
)

@Composable
fun AppsGrid(
    modifier: Modifier = Modifier,
    backdrop: LayerBackdrop = rememberLayerBackdrop(),
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
                    backdrop = backdrop,
                    appIcon = painterResource(id = item.icon),
                    appName = item.title,
                )
            }
        }
    }
}

@Preview
@Composable
fun AppsGridPreview() {
    TrebleKitTheme {
        AppsGrid(list = miniProgramList)
    }
}

val miniProgramList: ArrayList<MiniProgramItem> = arrayListOf(
    MiniProgramItem(
        title = "TrebleKit",
        icon = ResLogo.TREBLEKIT_LOGO,
    ),
    MiniProgramItem(
        title = "TrebleKit",
        icon = ResLogo.TREBLEKIT_LOGO,
    ),
    MiniProgramItem(
        title = "TrebleKit",
        icon = ResLogo.TREBLEKIT_LOGO,
    ),
    MiniProgramItem(
        title = "TrebleKit",
        icon = ResLogo.TREBLEKIT_LOGO,
    ),
    MiniProgramItem(
        title = "TrebleKit",
        icon = ResLogo.TREBLEKIT_LOGO,
    ),
    MiniProgramItem(
        title = "TrebleKit",
        icon = ResLogo.TREBLEKIT_LOGO,
    ),
    MiniProgramItem(
        title = "TrebleKit",
        icon = ResLogo.TREBLEKIT_LOGO,
    ),
    MiniProgramItem(
        title = "TrebleKit",
        icon = ResLogo.TREBLEKIT_LOGO,
    ),
)