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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.scale
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
import com.kyant.capsule.ContinuousCapsule
import com.kyant.liquidglass.GlassStyle
import com.kyant.liquidglass.LiquidGlassProviderState
import com.kyant.liquidglass.liquidGlass
import com.kyant.liquidglass.liquidGlassProvider
import com.kyant.liquidglass.material.GlassMaterial
import com.kyant.liquidglass.refraction.InnerRefraction
import com.kyant.liquidglass.refraction.RefractionAmount
import com.kyant.liquidglass.refraction.RefractionHeight
import com.kyant.liquidglass.rememberLiquidGlassProviderState
import com.kyant.liquidglass.shadow.GlassShadow
import io.treblekit.app.ui.navigation.NavigationItem
import io.treblekit.app.ui.navigation.PageList
import io.treblekit.app.ui.theme.AppBackgroundColor
import io.treblekit.app.ui.theme.TrebleKitTheme
import io.treblekit.app.ui.utils.navigateTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun <T : Any> TKNavBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    pages: List<NavigationItem<T>>,
    useLiquidGlass: Boolean,
) {
    val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = navBackStackEntry?.destination
    val blockProviderState = rememberLiquidGlassProviderState(
        backgroundColor = null,
    )
    val animationScope: CoroutineScope = rememberCoroutineScope()
    var isDragging: Boolean by remember {
        mutableStateOf(value = false)
    }
    val offset: Animatable<Float, AnimationVector1D> = remember {
        Animatable(initialValue = 0f)
    }
    val padding: Dp = 4.dp
    val paddingPx: Int = with(
        receiver = LocalDensity.current,
    ) {
        padding.roundToPx()
    }
    var selectedIndexState: Int by remember {
        mutableIntStateOf(value = 0)
    }
    val scaleXFraction: Float by animateFloatAsState(
        targetValue = if (!isDragging) 0f else 1f,
        animationSpec = spring(
            dampingRatio = 0.5f,
            stiffness = 300f,
        ),
    )
    val scaleYFraction: Float by animateFloatAsState(
        targetValue = if (!isDragging) 0f else 1f,
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
        contentPadding = PaddingValues.Zero,
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 64.dp)
                .padding(horizontal = 32.dp)
                .drawWithContent {
                    scale(
                        scaleX = lerp(
                            start = 0.97f,
                            stop = 1.0f,
                            fraction = scaleYFraction,
                        ),
                        scaleY = lerp(
                            start = 0.97f,
                            stop = 1.0f,
                            fraction = scaleYFraction,
                        ),
                    ) {
                        this@drawWithContent.drawContent()
                    }
                },
        ) {
            val widthWithoutPaddings: Float =
                (constraints.maxWidth.toFloat() - paddingPx * 2f).fastCoerceAtLeast(minimumValue = 0f)
            val tabWidth: Float = if (pages.isEmpty()) 0f else widthWithoutPaddings / pages.size
            val maxWidth: Float =
                (widthWithoutPaddings - tabWidth).fastCoerceAtLeast(minimumValue = 0f)
            LaunchedEffect(
                key1 = selectedIndexState,
                key2 = tabWidth,
                key3 = isDragging,
            ) {
                if (tabWidth > 0 && !isDragging) {
                    offset.animateTo(
                        targetValue = (selectedIndexState * tabWidth).fastCoerceIn(
                            minimumValue = 0f,
                            maximumValue = maxWidth,
                        ),
                        animationSpec = SpringSpec(
                            dampingRatio = 0.8f,
                            stiffness = 380f,
                        ),
                    )
                }
            }
            LaunchedEffect(key1 = currentDestination) {
                val currentIndex: Int = pages.indexOfFirst { page ->
                    return@indexOfFirst currentDestination?.hierarchy?.any {
                        return@any it.hasRoute(route = page.route::class)
                    } == true
                }
                if (currentIndex != -1) {
                    selectedIndexState = currentIndex
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
                            targetValue = if (selectedIndexState == index && !isDragging) {
                                0.8f
                            } else {
                                0f
                            },
                            animationSpec = spring(
                                dampingRatio = 0.8f,
                                stiffness = 200f,
                            ),
                        )
                        val contentColor: Color by animateColorAsState(
                            targetValue = if (selectedIndexState == index && !isDragging) {
                                MaterialTheme.colorScheme.onPrimaryContainer
                            } else {
                                Color(color = 0xff8E8E9E)
                            },
                            animationSpec = spring(
                                dampingRatio = 0.8f,
                                stiffness = 200f,
                            ),
                        )
                        Column(
                            modifier = modifier
                                .weight(weight = 1f)
                                .clip(shape = ContinuousCapsule)
                                .drawBehind {
                                    drawRect(
                                        color = backgroundColor,
                                        alpha = backgroundAlpha,
                                    )
                                }
                                .pointerInput(key1 = Unit) {
                                    detectTapGestures {
                                        if (selectedIndexState != index) {
                                            selectedIndexState = index
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
                                                    isDragging = true
                                                    delay(timeMillis = 200)
                                                    isDragging = false
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
                        color = AppBackgroundColor,
                        shape = ContinuousCapsule,
                    )
                    .liquidGlass(
                        state = blockProviderState,
                        style = GlassStyle(
                            shape = ContinuousCapsule,
                            innerRefraction = InnerRefraction(
                                height = RefractionHeight(
                                    value = animateFloatAsState(
                                        targetValue = if (!isDragging) {
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
                        onDragStarted = {
                            isDragging = true
                        },
                        onDragStopped = { velocity ->
                            isDragging = false
                            val currentIndex = offset.value / tabWidth
                            val targetIndex = when {
                                velocity > 0f -> ceil(x = currentIndex).toInt()
                                velocity < 0f -> floor(x = currentIndex).toInt()
                                else -> currentIndex.fastRoundToInt()
                            }.fastCoerceIn(
                                minimumValue = 0,
                                maximumValue = pages.lastIndex,
                            )
                            if (selectedIndexState != targetIndex) {
                                selectedIndexState = targetIndex
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
    return this then when (style) {
        NavBarStyle.LiquidGlass -> Modifier.liquidGlassProvider(
            state = blockProviderState,
        )

        NavBarStyle.Material3 -> Modifier
    }
}

@Stable
@Composable
private fun Modifier.navBarContainerStyle(style: NavBarStyle): Modifier {
    val providerState = rememberLiquidGlassProviderState(
        backgroundColor = AppBackgroundColor
    )
    val glassStyle = GlassStyle(
        shape = ContinuousCapsule,
        innerRefraction = InnerRefraction(
            height = RefractionHeight(value = 12.dp), amount = RefractionAmount.Half
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
            .clip(shape = ContinuousCapsule)
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
                useLiquidGlass = true,
                pages = PageList,
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
                useLiquidGlass = false,
                pages = PageList,
                navController = rememberNavController(),
            )
        }
    }
}