package io.treblekit.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCoerceAtLeast
import androidx.compose.ui.util.fastCoerceIn
import androidx.compose.ui.util.fastRoundToInt
import androidx.compose.ui.util.lerp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kyant.liquidglass.GlassStyle
import com.kyant.liquidglass.LiquidGlassProviderState
import com.kyant.liquidglass.liquidGlass
import com.kyant.liquidglass.liquidGlassProvider
import com.kyant.liquidglass.material.GlassMaterial
import com.kyant.liquidglass.refraction.InnerRefraction
import com.kyant.liquidglass.refraction.RefractionAmount
import com.kyant.liquidglass.refraction.RefractionHeight
import com.kyant.liquidglass.rememberLiquidGlassProviderState
import com.kyant.liquidglass.sampler.ExperimentalLuminanceSamplerApi
import com.kyant.liquidglass.shadow.GlassShadow
import io.treblekit.app.ui.navigation.HomePage
import io.treblekit.app.ui.navigation.NavigationItem
import io.treblekit.app.ui.navigation.PageList
import io.treblekit.app.ui.theme.AppBackgroundColor
import io.treblekit.app.ui.theme.TrebleKitTheme
import io.treblekit.app.ui.utils.navigateTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.floor

@OptIn(
    ExperimentalLuminanceSamplerApi::class,
    ExperimentalLuminanceSamplerApi::class,
    FlowPreview::class,
)
@Composable
fun <T : Any> TKNavBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    pages: List<NavigationItem<T>>,
    startDestination: T,
    background: Color,
    useLiquidGlass: Boolean,
) {
    val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = navBackStackEntry?.destination
    val blockProviderState = rememberLiquidGlassProviderState(backgroundColor = null)
    val animationScope: CoroutineScope = rememberCoroutineScope()
    val isDragging: MutableState<Boolean> = remember { mutableStateOf(value = false) }
    val offset: Animatable<Float, AnimationVector1D> = remember { Animatable(initialValue = 0f) }
    val padding: Dp = 4.dp
    val paddingPx: Int = with(receiver = LocalDensity.current) { padding.roundToPx() }
    val selectedIndexState: MutableIntState = remember {
        var startIndex = 0
        pages.forEachIndexed { index, item ->
            if (item.route == startDestination) {
                startIndex = index
            }
        }
        mutableIntStateOf(value = startIndex)
    }
    val scaleXFraction: Float by animateFloatAsState(
        targetValue = if (!isDragging.value) 0f else 1f,
        animationSpec = spring(
            dampingRatio = 0.5f,
            stiffness = 300f,
        ),
    )
    val scaleYFraction: Float by animateFloatAsState(
        targetValue = if (!isDragging.value) 0f else 1f,
        animationSpec = spring(
            dampingRatio = 0.5f,
            stiffness = 600f,
        ),
    )
    BottomAppBar(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        containerColor = Color.Transparent,
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 64.dp)
                .padding(horizontal = 32.dp),
        ) {
            val widthWithoutPaddings: Float =
                (constraints.maxWidth.toFloat() - paddingPx * 2f).fastCoerceAtLeast(minimumValue = 0f)
            val tabWidth: Float = if (pages.isEmpty()) 0f else widthWithoutPaddings / pages.size
            val maxWidth: Float =
                (widthWithoutPaddings - tabWidth).fastCoerceAtLeast(minimumValue = 0f)
            LaunchedEffect(
                key1 = selectedIndexState.intValue,
                key2 = tabWidth,
                key3 = isDragging,
            ) {
                if (tabWidth > 0 && !isDragging.value) {
                    offset.animateTo(
                        targetValue = (selectedIndexState.intValue * tabWidth).fastCoerceIn(
                            0f, maxWidth
                        ),
                        animationSpec = SpringSpec(
                            dampingRatio = 0.8f,
                            stiffness = 380f,
                        ),
                    )
                }
            }
            LaunchedEffect(key1 = currentDestination) {
                snapshotFlow {
                    currentDestination?.hierarchy
                }.debounce(
                    timeoutMillis = 150,
                ).collectLatest {
                    for (page in pages) {
                        val isCurrent: Boolean? = currentDestination?.hierarchy?.any {
                            return@any it.hasRoute(route = page.route::class)
                        }
                        if (isCurrent == true) {
                            pages.forEachIndexed { index, item ->
                                if (item.route == page.route) {
                                    selectedIndexState.intValue = index
                                }
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .navBarBlockProvider(
                        style = getStyle(useLiquidGlass = useLiquidGlass),
                        blockProviderState = blockProviderState,
                    )
                    .navBarContainerStyle(
                        style = getStyle(useLiquidGlass = useLiquidGlass),
                    )
                    .padding(all = padding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                pages.forEachIndexed { index, page ->
                    key(page) {
                        val backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer
                        val backgroundAlpha: Float by animateFloatAsState(
                            targetValue = if (selectedIndexState.intValue == index && !isDragging.value) {
                                0.8f
                            } else {
                                0f
                            }, animationSpec = spring(
                                dampingRatio = 0.8f,
                                stiffness = 200f,
                            )
                        )
                        val contentColor: Color by animateColorAsState(
                            targetValue = if (selectedIndexState.intValue == index && !isDragging.value) {
                                MaterialTheme.colorScheme.onPrimaryContainer
                            } else {
                                Color(color = 0xff8E8E9E)
                            }, animationSpec = spring(
                                dampingRatio = 0.8f, stiffness = 200f
                            )
                        )
                        Column(
                            modifier = modifier
                                .weight(weight = 1f)
                                .clip(shape = RoundedCornerShape(percent = 50))
                                .drawBehind {
                                    drawRect(
                                        color = backgroundColor,
                                        alpha = backgroundAlpha,
                                    )
                                }
                                .pointerInput(key1 = Unit) {
                                    detectTapGestures {
                                        if (selectedIndexState.intValue != index) {
                                            selectedIndexState.intValue = index
                                            navigateTo(
                                                navController = navController,
                                                route = pages[index].route,
                                            )
                                            animationScope.launch {
                                                launch {
                                                    offset.animateTo(
                                                        targetValue = (index * tabWidth).fastCoerceIn(
                                                            0f, maxWidth
                                                        ),
                                                        animationSpec = spring(
                                                            dampingRatio = 0.8f,
                                                            stiffness = 200f,
                                                        ),
                                                    )
                                                }
                                                launch {
                                                    isDragging.value = true
                                                    delay(timeMillis = 200)
                                                    isDragging.value = false
                                                }
                                            }
                                        }
                                    }
                                }
                                .height(height = 56.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                space = 2.dp,
                                alignment = Alignment.CenterVertically,
                            ),
                        ) {
                            Icon(
                                modifier = Modifier,
                                imageVector = page.icon,
                                contentDescription = null,
                                tint = contentColor,
                            )
                            Text(
                                text = page.label,
                                color = contentColor,
                            )
                        }
                    }
                }
            }
            if (useLiquidGlass) Spacer(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val width = tabWidth.fastRoundToInt()
                        val height = 56.dp.roundToPx()
                        val placeable = measurable.measure(
                            Constraints.fixed(
                                width = (width * lerp(
                                    start = 1f,
                                    stop = 1.5f,
                                    fraction = scaleXFraction,
                                )).fastRoundToInt(),
                                height = (height * lerp(
                                    start = 1f,
                                    stop = 1.5f,
                                    fraction = scaleYFraction,
                                )).fastRoundToInt(),
                            ),
                        )
                        layout(width = width, height = height) {
                            placeable.place(
                                x = (width - placeable.width) / 2 + paddingPx,
                                y = (height - placeable.height) / 2 + paddingPx
                            )
                        }
                    }
                    .drawWithContent {
                        translate(
                            left = 0f,
                            top = lerp(
                                start = 0f,
                                stop = 4f,
                                fraction = scaleYFraction,
                            ).dp.toPx(),
                        ) {
                            this@drawWithContent.drawContent()
                        }
                    }
                    .graphicsLayer {
                        translationX = offset.value
                        scaleX = lerp(
                            start = 1f,
                            stop = 0.9f,
                            fraction = scaleXFraction,
                        )
                        scaleY = lerp(
                            start = 1f,
                            stop = 0.9f,
                            fraction = scaleYFraction,
                        )
                        transformOrigin = TransformOrigin(
                            pivotFractionX = 0f,
                            pivotFractionY = 0f,
                        )
                    }
                    .background(
                        color = background,
                        shape = RoundedCornerShape(percent = 50),
                    )
                    .liquidGlass(
                        state = blockProviderState,
                        style = GlassStyle(
                            shape = RoundedCornerShape(percent = 50),
                            innerRefraction = InnerRefraction(
                                height = RefractionHeight(
                                    value = animateFloatAsState(
                                        targetValue = if (!isDragging.value) {
                                            0f
                                        } else {
                                            10f
                                        }
                                    ).value.dp
                                ),
                                amount = RefractionAmount.Half,
                            ),
                            material = GlassMaterial.None,
                        ),
                    )
                    .draggable(
                        state = rememberDraggableState { delta ->
                            animationScope.launch {
                                offset.snapTo(
                                    targetValue = (offset.value + delta).fastCoerceIn(
                                        minimumValue = 0f,
                                        maximumValue = maxWidth,
                                    ),
                                )
                            }
                        },
                        orientation = Orientation.Horizontal,
                        startDragImmediately = true,
                        onDragStarted = { isDragging.value = true },
                        onDragStopped = { velocity ->
                            isDragging.value = false
                            val currentIndex = offset.value / tabWidth
                            val targetIndex = when {
                                velocity > 0f -> ceil(x = currentIndex).toInt()
                                velocity < 0f -> floor(x = currentIndex).toInt()
                                else -> currentIndex.fastRoundToInt()
                            }.fastCoerceIn(
                                minimumValue = 0,
                                maximumValue = pages.lastIndex,
                            )
                            if (selectedIndexState.intValue != targetIndex) {
                                selectedIndexState.intValue = targetIndex
                                navigateTo(
                                    navController = navController,
                                    route = pages[targetIndex].route,
                                )
                            }
                            animationScope.launch {
                                offset.animateTo(
                                    targetValue = (targetIndex * tabWidth).fastCoerceIn(
                                        minimumValue = 0f,
                                        maximumValue = maxWidth,
                                    ),
                                    animationSpec = spring(
                                        dampingRatio = 0.8f,
                                        stiffness = 200f,
                                    ),
                                )
                            }
                        },
                    )
            )
        }
    }
}

private enum class NavBarStyle {
    LiquidGlass, Material3
}

private fun getStyle(useLiquidGlass: Boolean): NavBarStyle {
    return if (useLiquidGlass) {
        NavBarStyle.LiquidGlass
    } else {
        NavBarStyle.Material3
    }
}

@Stable
@Composable
private fun Modifier.navBarBlockProvider(
    style: NavBarStyle,
    blockProviderState: LiquidGlassProviderState,
): Modifier {
    return when (style) {
        NavBarStyle.LiquidGlass -> this then Modifier.liquidGlassProvider(
            state = blockProviderState,
        )
        NavBarStyle.Material3 -> this
    }
}

@Stable
@Composable
private fun Modifier.navBarContainerStyle(style: NavBarStyle): Modifier {
    val providerState = rememberLiquidGlassProviderState(
        backgroundColor = AppBackgroundColor
    )
    val glassStyle = GlassStyle(
        shape = RoundedCornerShape(percent = 50),
        innerRefraction = InnerRefraction(
            height = RefractionHeight(value = 12.dp),
            amount = RefractionAmount.Half
        ),
        shadow = GlassShadow(
            elevation = 4.dp,
            brush = SolidColor(
                value = Color.Black.copy(
                    alpha = 0.15f,
                ),
            ),
        ),
        material = GlassMaterial(
            brush = SolidColor(value = Color(color = 0xff434056)),
            alpha = 0.5f,
        ),
    )
    return this then when (style) {
        NavBarStyle.LiquidGlass -> Modifier.liquidGlass(
            state = providerState,
            style = glassStyle,
        )

        NavBarStyle.Material3 -> Modifier
            .clip(shape = RoundedCornerShape(percent = 50))
            .background(color = Color(color = 0xff434056))
    }
}

@Preview
@Composable
private fun TKNavBarLiquidGlassPreview() {
    TrebleKitTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = AppBackgroundColor)
        ) {
            TKNavBar(
                background = AppBackgroundColor,
                useLiquidGlass = true,
                pages = PageList,
                startDestination = HomePage,
                navController = rememberNavController(),
            )
        }
    }
}

@Preview
@Composable
private fun TKNavBarMaterialPreview() {
    TrebleKitTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = AppBackgroundColor)
        ) {
            TKNavBar(
                background = AppBackgroundColor,
                useLiquidGlass = false,
                pages = PageList,
                startDestination = HomePage,
                navController = rememberNavController(),
            )
        }
    }
}