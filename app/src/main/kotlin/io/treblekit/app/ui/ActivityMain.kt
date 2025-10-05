package io.treblekit.app.ui

import android.os.Build
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberCombinedBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.refraction
import com.kyant.backdrop.effects.vibrancy
import com.kyant.backdrop.highlight.Highlight
import com.kyant.backdrop.highlight.HighlightStyle
import com.kyant.capsule.ContinuousCapsule
import com.kyant.capsule.ContinuousRoundedRectangle
import io.treblekit.app.R
import io.treblekit.app.ui.components.EffectView
import io.treblekit.app.ui.destination.DashboardDestination
import io.treblekit.app.ui.destination.PlatformDestination
import io.treblekit.app.ui.theme.AndroidGreen
import io.treblekit.app.ui.theme.AppBackground
import io.treblekit.app.ui.theme.CapsuleEdgePadding
import io.treblekit.app.ui.theme.CapsuleHeight
import io.treblekit.app.ui.theme.CapsuleWidth
import io.treblekit.app.ui.theme.TrebleKitTheme
import io.treblekit.app.ui.utils.NoOnClick
import io.treblekit.app.ui.utils.isCurrentPagerDestination
import io.treblekit.app.ui.utils.navigateToPagerRoute
import io.treblekit.app.ui.utils.rememberUISensor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityMain() {
    val coroutineScope = rememberCoroutineScope()
    val pageState = rememberPagerState(
        pageCount = { appDestination.size },
    )
    var showDialog by remember {
        mutableStateOf(value = false)
    }
    val backgroundBackdrop = rememberLayerBackdrop()
    val effectBackdrop = rememberLayerBackdrop()
    val backdrop = rememberCombinedBackdrop(
        backdrop1 = backgroundBackdrop,
        backdrop2 = effectBackdrop,
    )
    val tint = MaterialTheme.colorScheme.primaryContainer
    val uiSensor = rememberUISensor()
    val animationScope = rememberCoroutineScope()
    val progressAnimation = remember { Animatable(0f) }
    Box {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppBackground)
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
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                    ),
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
                                .graphicsLayer {
                                    val progress = progressAnimation.value
                                    val scale = lerp(
                                        start = 1f,
                                        stop = 1.1f,
                                        amount = progress,
                                    )
                                    scaleX = scale
                                    scaleY = scale
                                }
                                .drawBackdrop(
                                    backdrop = backdrop,
                                    shape = { ContinuousCapsule },
                                    effects = {
                                        vibrancy()
                                        blur(blurRadius = 16f.dp.toPx())
                                        refraction(
                                            height = 24f.dp.toPx(),
                                            amount = 48f.dp.toPx(),
                                            hasDepthEffect = true
                                        )
                                    },
                                    highlight = {
                                        Highlight(
                                            style = HighlightStyle.Default(
                                                angle = uiSensor.gravityAngle
                                            ),
                                        )
                                    },
                                    layerBlock = {
                                        val progress = progressAnimation.value
                                        val scale = lerp(
                                            start = 1f,
                                            stop = 1.1f,
                                            amount = progress,
                                        )
                                        scaleX = scale
                                        scaleY = scale
                                    }
                                )
                                .clickable {

                                }
                                .pointerInput(key1 = animationScope) {
                                    val animationSpec = spring(
                                        dampingRatio = 0.5f,
                                        stiffness = 300f,
                                        visibilityThreshold = 0.001f,
                                    )
                                    awaitEachGesture {
                                        awaitFirstDown()
                                        animationScope.launch {
                                            progressAnimation.animateTo(
                                                targetValue = 1f,
                                                animationSpec = animationSpec,
                                            )
                                        }
                                        waitForUpOrCancellation()
                                        animationScope.launch {
                                            progressAnimation.animateTo(
                                                targetValue = 0f,
                                                animationSpec = animationSpec,
                                            )
                                        }
                                    }
                                },
                        ) {
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
                                        tint = Color(color = 0xff8E8E9E),
                                        modifier = Modifier.size(size = 20.dp),
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
                        }
                    },
                    actions = {},
                )
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    containerColor = Color.Transparent,
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
                            iconContentColor = AndroidGreen,
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
                                tint = AndroidGreen,
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
                                            shape = { ContinuousRoundedRectangle(size = 16.dp) },
                                            effects = {
                                                vibrancy()
                                                blur(blurRadius = 16f.dp.toPx())
                                                refraction(
                                                    height = 24f.dp.toPx(),
                                                    amount = 48f.dp.toPx(),
                                                    hasDepthEffect = true
                                                )
                                            },
                                            onDrawSurface = {

                                                drawRect(
                                                    color = tint.copy(alpha = 0.5f),
                                                    blendMode = BlendMode.Hue,
                                                )
                                            },
                                            highlight = {
                                                Highlight(
                                                    style = HighlightStyle.Default(
                                                        angle = uiSensor.gravityAngle
                                                    ),
                                                )
                                            }
                                        ),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    appDestination.forEach { page ->
                                        val isCurrent = pageState.isCurrentPagerDestination(route = page.route)
                                        IconButton(
                                            onClick = {
                                                if (!isCurrent) coroutineScope.launch {
                                                    pageState.navigateToPagerRoute(route = page.route)
                                                }
                                            },
                                            modifier = Modifier.wrapContentSize(),
                                        ) {
                                            val iconAlpha: Float by animateFloatAsState(
                                                targetValue = if (isCurrent) 1f else 0.5f,
                                                animationSpec = spring(
                                                    dampingRatio = 0.8f,
                                                    stiffness = 200f,
                                                ),
                                            )
                                            Icon(
                                                imageVector = if (isCurrent) {
                                                    page.selectedIcon
                                                } else {
                                                    page.icon
                                                },
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .wrapContentSize()
                                                    .alpha(alpha = iconAlpha),
                                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
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
                                            refraction(
                                                height = 24f.dp.toPx(),
                                                amount = 48f.dp.toPx(),
                                                hasDepthEffect = true
                                            )
                                        },
                                        onDrawSurface = {
                                            drawRect(
                                                color = tint.copy(alpha = 0.5f),
                                                blendMode = BlendMode.Hue,
                                            )
                                        },
                                        highlight = {
                                            Highlight(
                                                style = HighlightStyle.Default(
                                                    angle = uiSensor.gravityAngle
                                                ),
                                            )
                                        }
                                    ),
                                containerColor = Color.Transparent,
                                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                            )
                        }
                    },
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
                                pageState.navigateToPagerRoute(
                                    route = PlatformDestination
                                )
                            }
                        },
                    )

                    PlatformDestination -> PlatformDestination(
                        animateToDashboard = {
                            coroutineScope.launch {
                                pageState.navigateToPagerRoute(
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
    }
}

@Preview
@Composable
private fun ActivityMainPreview() {
    TrebleKitTheme {
        ActivityMain()
    }
}