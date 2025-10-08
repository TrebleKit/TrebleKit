package io.treblekit.app.ui.activity

import android.os.Build
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberCombinedBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import com.kyant.capsule.ContinuousCapsule
import com.kyant.capsule.ContinuousRoundedRectangle
import io.treblekit.app.R
import io.treblekit.app.ui.components.EffectView
import io.treblekit.app.ui.components.OverlayView
import io.treblekit.app.ui.destination.DashboardDestination
import io.treblekit.app.ui.destination.PlatformDestination
import io.treblekit.app.ui.navigation.DashboardDestination
import io.treblekit.app.ui.navigation.PlatformDestination
import io.treblekit.app.ui.navigation.appDestination
import io.treblekit.app.ui.theme.TrebleKitTheme
import io.treblekit.app.ui.theme.androidGreen
import io.treblekit.app.ui.theme.appBackground
import io.treblekit.app.ui.theme.capsuleContainer
import io.treblekit.app.ui.theme.capsuleEdgePadding
import io.treblekit.app.ui.theme.capsuleHeight
import io.treblekit.app.ui.theme.capsuleIndent
import io.treblekit.app.ui.theme.capsuleWidth
import io.treblekit.app.ui.theme.topBarPaddingExcess
import io.treblekit.app.ui.utils.NoOnClick
import io.treblekit.app.ui.utils.isCurrentDestination
import io.treblekit.app.ui.utils.navigateToRoute
import io.treblekit.app.ui.utils.rememberCapsulePadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityMain() {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val pageState: PagerState = rememberPagerState { appDestination.size }
    var showDialog: Boolean by remember { mutableStateOf(value = false) }
    val backgroundBackdrop: LayerBackdrop = rememberLayerBackdrop()
    val effectBackdrop: LayerBackdrop = rememberLayerBackdrop()
    val backdrop: Backdrop = rememberCombinedBackdrop(
        backdrop1 = backgroundBackdrop,
        backdrop2 = effectBackdrop,
    )
    val fabTint: Color = MaterialTheme.colorScheme.primaryContainer
    val inspection: Boolean = LocalInspectionMode.current
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = appBackground)
                .layerBackdrop(backdrop = backgroundBackdrop),
        )
        EffectView(
            modifier = Modifier.layerBackdrop(backdrop = effectBackdrop)
        )
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
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
                                .padding(start = capsuleEdgePadding - topBarPaddingExcess)
                                .width(width = capsuleWidth)
                                .height(height = capsuleHeight)
                                .drawBackdrop(
                                    backdrop = backdrop,
                                    shape = {
                                        ContinuousCapsule
                                    },
                                    effects = {
                                        vibrancy()
                                        blur(blurRadius = 16f.dp.toPx())
                                        lens(
                                            refractionHeight = 24f.dp.toPx(),
                                            refractionAmount = 48f.dp.toPx(),
                                            hasDepthEffect = true,
                                        )
                                    },
                                    onDrawSurface = {
                                        drawRect(
                                            color = capsuleContainer,
                                            blendMode = BlendMode.Hue,
                                        )
                                    },
                                )
                                .clickable {

                                }) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
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
                                    )
                                    Text(
                                        text = "搜索",
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .padding(start = 6.dp),
                                        fontSize = 13.sp,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                        }
                    },
                    actions = {
                        if (inspection) {
                            Box(
                                modifier = Modifier
                                    .padding(end = capsuleEdgePadding - topBarPaddingExcess)
                                    .width(width = capsuleWidth)
                                    .height(height = capsuleHeight)
                                    .drawBackdrop(
                                        backdrop = backdrop,
                                        shape = {
                                            ContinuousCapsule
                                        },
                                        effects = {
                                            vibrancy()
                                            blur(blurRadius = 16f.dp.toPx())
                                            lens(
                                                refractionHeight = 24f.dp.toPx(),
                                                refractionAmount = 48f.dp.toPx(),
                                                hasDepthEffect = true,
                                            )
                                        },
                                        onDrawSurface = {
                                            drawRect(
                                                color = capsuleContainer,
                                                blendMode = BlendMode.Hue,
                                            )
                                        },
                                    ),
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
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
                                            .padding(vertical = capsuleIndent)
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
                            }
                        } else {
                            Spacer(
                                modifier = Modifier.padding(
                                    paddingValues = rememberCapsulePadding(
                                        excess = topBarPaddingExcess,
                                    ),
                                ),
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                    ),
                )
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    actions = {
                        if (showDialog) AlertDialog(
                            onDismissRequest = {
                                showDialog = false
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        showDialog = false
                                    },
                                ) {
                                    Text(text = "确定")
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.Android,
                                    contentDescription = null,
                                )
                            },
                            iconContentColor = androidGreen,
                            title = {
                                Text(text = "Android")
                            },
                            text = {
                                Text(text = "Android API ${Build.VERSION.SDK_INT}")
                            },
                        )
                        IconButton(
                            onClick = {
                                showDialog = true
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Android,
                                contentDescription = null,
                                tint = androidGreen,
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
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .sizeIn(minWidth = 80.dp),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(end = 4.dp)
                                        .defaultMinSize(minHeight = 56.dp)
                                        .drawBackdrop(
                                            backdrop = backdrop,
                                            shape = {
                                                ContinuousRoundedRectangle(size = 16.dp)
                                            },
                                            effects = {
                                                vibrancy()
                                                blur(blurRadius = 16f.dp.toPx())
                                                lens(
                                                    refractionHeight = 24f.dp.toPx(),
                                                    refractionAmount = 48f.dp.toPx(),
                                                    hasDepthEffect = true
                                                )
                                            },
                                            onDrawSurface = {
                                                drawRect(
                                                    color = fabTint.copy(alpha = 0.5f),
                                                    blendMode = BlendMode.Hue,
                                                )
                                            },
                                        ),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    appDestination.forEach { page ->
                                        val isCurrent = pageState.isCurrentDestination(
                                            route = page.route,
                                        )
                                        val iconAlpha: Float by animateFloatAsState(
                                            targetValue = if (isCurrent) 1f else 0.5f,
                                            animationSpec = spring(
                                                dampingRatio = 0.8f,
                                                stiffness = 200f,
                                            ),
                                        )
                                        IconButton(
                                            onClick = {
                                                if (!isCurrent) coroutineScope.launch {
                                                    pageState.navigateToRoute(
                                                        route = page.route,
                                                    )
                                                }
                                            },
                                            modifier = Modifier.wrapContentSize(),
                                            colors = IconButtonDefaults.iconButtonColors(
                                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                                    alpha = iconAlpha,
                                                ),
                                            ),
                                        ) {
                                            Icon(
                                                imageVector = if (isCurrent) {
                                                    page.selectedIcon
                                                } else {
                                                    page.icon
                                                },
                                                contentDescription = null,
                                                modifier = Modifier.wrapContentSize(),
                                            )
                                        }
                                    }
                                }
                            }
                            ExtendedFloatingActionButton(
                                text = {
                                    Text(
                                        text = "返回",
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.TwoTone.Home,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                },
                                onClick = NoOnClick,
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(start = 4.dp)
                                    .drawBackdrop(
                                        backdrop = backdrop,
                                        shape = {
                                            ContinuousRoundedRectangle(size = 16.dp)
                                        },
                                        effects = {
                                            vibrancy()
                                            blur(blurRadius = 16f.dp.toPx())
                                            lens(
                                                refractionHeight = 24f.dp.toPx(),
                                                refractionAmount = 48f.dp.toPx(),
                                                hasDepthEffect = true
                                            )
                                        },
                                        onDrawSurface = {
                                            drawRect(
                                                color = fabTint.copy(alpha = 0.5f),
                                                blendMode = BlendMode.Hue,
                                            )
                                        },
                                    ),
                                containerColor = Color.Transparent,
                                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                            )
                        }
                    },
                    containerColor = Color.Transparent,
                )
            },
            containerColor = Color.Transparent,
        ) { innerPadding ->
            HorizontalPager(
                state = pageState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = innerPadding),
                userScrollEnabled = false,
            ) { page ->
                when (appDestination[page].route) {
                    DashboardDestination -> DashboardDestination(
                        popBackStack = {},
                        animateToEcosed = {
                            coroutineScope.launch {
                                pageState.navigateToRoute(
                                    route = PlatformDestination
                                )
                            }
                        },
                    )

                    PlatformDestination -> PlatformDestination(
                        animateToDashboard = {
                            coroutineScope.launch {
                                pageState.navigateToRoute(
                                    route = DashboardDestination
                                )
                            }
                        },
                    )

                    else -> Box(
                        modifier = Modifier.fillMaxSize(),
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
        OverlayView()
    }
}

@Preview
@Composable
private fun ActivityMainPreview() {
    TrebleKitTheme {
        ActivityMain()
    }
}