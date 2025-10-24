package io.treblekit.ui.destination

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.imageloading.rememberDrawablePainter
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import com.kyant.capsule.ContinuousCapsule
import com.kyant.capsule.ContinuousRoundedRectangle
import io.treblekit.R
import io.treblekit.ui.navigation.PlatformDestination
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.utils.NoOnClick
import io.treblekit.ui.utils.navigateToRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DashboardDestination(
    modifier: Modifier = Modifier,
    pageState: PagerState? = null,
    backdrop: Backdrop = rememberLayerBackdrop(),
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
        val primaryContainerTint = MaterialTheme.colorScheme.primaryContainer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    start = 16.dp,
                    top = 15.dp,
                    end = 16.dp,
                    bottom = 15.dp,
                )
                .drawBackdrop(
                    backdrop = backdrop,
                    shape = {
                        ContinuousRoundedRectangle(size = 16.dp)
                    },
                    effects = {
                        vibrancy()
                        blur(radius = 2f.dp.toPx())
                        lens(
                            refractionHeight = 12f.dp.toPx(),
                            refractionAmount = 24f.dp.toPx(),
                            depthEffect = true,
                        )
                    },
                    onDrawSurface = {
                        drawRect(
                            color = primaryContainerTint.copy(alpha = 0.8f),
                            blendMode = BlendMode.Hue,
                        )
                    },
                )
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
//        Surface(
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()
//                .padding(
//                    start = 16.dp,
//                    top = 15.dp,
//                    end = 16.dp,
//                    bottom = 15.dp,
//                ),
//            shape = ContinuousRoundedRectangle(size = 16.dp),
//            color = MaterialTheme.colorScheme.secondaryContainer,
//        ) {
//            Column(
//                modifier = Modifier.padding(16.dp),
//            ) {
//                Row {
//                    Text(
//                        text = "互联互通",
//                        fontSize = 14.sp,
//                    )
//                }
//                Row(
//                    modifier = Modifier,
//                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
//                ) {
//                    Column(
//                        modifier = Modifier.wrapContentSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .size(size = 40.dp)
//                                .clip(shape = RoundedCornerShape(size = 35.dp))
//                                .background(color = Color(color = 0xFF8E8E9E))
//                                .clickable(onClick = {}),
//                            contentAlignment = Alignment.Center,
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.Add,
//                                contentDescription = null,
//                            )
//                        }
//                        Text(
//                            text = "app",
//                            fontSize = 15.sp,
//                            color = Color.White,
//                            textAlign = TextAlign.Center,
//                        )
//                    }
//                    Column(
//                        modifier = Modifier.wrapContentSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .size(size = 40.dp)
//                                .clip(shape = RoundedCornerShape(size = 35.dp))
//                                .background(color = Color(color = 0xFF8E8E9E))
//                                .clickable(onClick = {}),
//                            contentAlignment = Alignment.Center,
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.Add,
//                                contentDescription = null,
//                            )
//                        }
//                        Text(
//                            text = "app",
//                            fontSize = 15.sp,
//                            color = Color.White,
//                            textAlign = TextAlign.Center,
//                        )
//                    }
//                    Column(
//                        modifier = Modifier.wrapContentSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .size(size = 40.dp)
//                                .clip(shape = RoundedCornerShape(size = 35.dp))
//                                .background(color = Color(color = 0xFF8E8E9E))
//                                .clickable(onClick = {}),
//                            contentAlignment = Alignment.Center,
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.Add,
//                                contentDescription = null,
//                            )
//                        }
//                        Text(
//                            text = "app",
//                            fontSize = 15.sp,
//                            color = Color.White,
//                            textAlign = TextAlign.Center,
//                        )
//                    }
//
//
//                }
//            }
//        }
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

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MPPlayer(
    modifier: Modifier = Modifier,
    pageState: PagerState? = null,
    backdrop: Backdrop = rememberLayerBackdrop(),
    popBackStack: () -> Unit = NoOnClick,
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
                backdrop = backdrop,
                onLaunch = popBackStack,
                style = AppItemStyle.Image,
                appIcon = rememberDrawablePainter(
                    drawable = AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_treblekit,
                    ),
                ),
                appName = "TrebleKit",
            )
            AppItem(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxSize(),
                backdrop = backdrop,
                onLaunch = {},
                style = AppItemStyle.Image,
                appIcon = rememberDrawablePainter(
                    drawable = AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_ebkit,
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
    backdrop: Backdrop = rememberLayerBackdrop(),
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
                .drawBackdrop(
                    backdrop = backdrop,
                    shape = {
                        ContinuousCapsule
                    },
                    effects = {
                        vibrancy()
                        blur(radius = 2f.dp.toPx())
                        lens(
                            refractionHeight = 12f.dp.toPx(),
                            refractionAmount = 24f.dp.toPx(),
                            depthEffect = false,
                        )
                    },
                    onDrawSurface = {
                        drawRect(
                            color = primaryContainerTint.copy(alpha = 0.8f),
                            blendMode = BlendMode.Hue,
                        )
                    },
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
    backdrop: Backdrop = rememberLayerBackdrop(),
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
    backdrop: Backdrop = rememberLayerBackdrop(),
    onLaunch: () -> Unit = NoOnClick,
    style: AppItemStyle,
    appIcon: Painter,
    appName: String,
) {
    val surfaceContainerHighestTint = MaterialTheme.colorScheme.surfaceContainerHighest
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(size = 60.dp)
                .drawBackdrop(
                    backdrop = backdrop,
                    shape = {
                        ContinuousCapsule
                    },
                    effects = {
                        vibrancy()
                        blur(radius = 2f.dp.toPx())
                        lens(
                            refractionHeight = 12f.dp.toPx(),
                            refractionAmount = 24f.dp.toPx(),
                            depthEffect = false,
                        )
                    },
                    onDrawSurface = {
                        drawRect(
                            color = surfaceContainerHighestTint.copy(alpha = 0.8f),
                            blendMode = BlendMode.Hue,
                        )
                    },
                )
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
                        .clip(shape = ContinuousCapsule)

                    AppItemStyle.Icon -> Modifier.size(
                        size = 30.dp,
                    )
                }.alpha(alpha = 0.8f)
            )
        }
        Text(
            text = appName,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onBackground,
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