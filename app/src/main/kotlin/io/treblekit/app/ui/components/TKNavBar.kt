package io.treblekit.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
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
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCoerceAtLeast
import androidx.compose.ui.util.fastCoerceIn
import androidx.compose.ui.util.fastRoundToInt
import androidx.compose.ui.util.lerp
import com.kyant.liquidglass.GlassStyle
import com.kyant.liquidglass.liquidGlass
import com.kyant.liquidglass.liquidGlassProvider
import com.kyant.liquidglass.material.GlassMaterial
import com.kyant.liquidglass.refraction.InnerRefraction
import com.kyant.liquidglass.refraction.RefractionAmount
import com.kyant.liquidglass.refraction.RefractionHeight
import com.kyant.liquidglass.rememberLiquidGlassProviderState
import com.kyant.liquidglass.sampler.ExperimentalLuminanceSamplerApi
import io.treblekit.app.ui.navigation.NavigationItem
import io.treblekit.app.ui.navigation.PageList
import io.treblekit.app.ui.theme.Background
import io.treblekit.app.ui.theme.TrebleKitTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.floor

@OptIn(ExperimentalLuminanceSamplerApi::class, ExperimentalLuminanceSamplerApi::class)
@Composable
fun <T> TKNavBar(
    modifier: Modifier = Modifier,
    background: Color,
    useMaterial: Boolean,
    pages: List<NavigationItem<T>>,
    selectedIndexState: MutableState<Int>,
    onTabSelected: (index: Int) -> Unit,
) {
    val providerState = rememberLiquidGlassProviderState(
        backgroundColor = background
    )
    val bottomTabsLiquidGlassProviderState = rememberLiquidGlassProviderState(
        backgroundColor = null
    )
    val animationScope = rememberCoroutineScope()
    val density = LocalDensity.current
    var isDragging by remember { mutableStateOf(false) }
    val offset = remember { Animatable(0f) }
    val padding = 4.dp
    val paddingPx = with(density) { padding.roundToPx() }



    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .defaultMinSize(minHeight = 80.dp)
            .windowInsetsPadding(insets = NavigationBarDefaults.windowInsets)
            .padding(horizontal = 32.dp, vertical = 8.dp)
            .selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .weight(weight = 1f)
                    .height(height = 64.dp)
                    .fillMaxWidth(),
            ) {
                val widthWithoutPaddings: Float =
                    (constraints.maxWidth.toFloat() - paddingPx * 2f).fastCoerceAtLeast(
                        minimumValue = 0f
                    )
                val tabWidth: Float = if (pages.isEmpty()) 0f else widthWithoutPaddings / pages.size
                val maxWidth: Float =
                    (widthWithoutPaddings - tabWidth).fastCoerceAtLeast(minimumValue = 0f)

                LaunchedEffect(
                    key1 = selectedIndexState.value,
                    key2 = tabWidth,
                    key3 = isDragging,
                ) {
                    if (tabWidth > 0 && !isDragging) {
                        offset.animateTo(
                            targetValue = (selectedIndexState.value * tabWidth).fastCoerceIn(
                                0f, maxWidth
                            ),
                            animationSpec = SpringSpec(
                                dampingRatio = 0.8f,
                                stiffness = 380f,
                            ),
                        )
                    }
                }

                Row(
                    modifier = if (useMaterial) Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(50.dp))
                        .background(Color(color = 0xff434056))
                        .padding(all = padding) else Modifier
                        .fillMaxSize()
                        .liquidGlassProvider(
                            state = bottomTabsLiquidGlassProviderState,
                        )
                        .liquidGlass(
                            state = providerState,
                            style = GlassStyle(
                                shape = RoundedCornerShape(percent = 50),
                                innerRefraction = InnerRefraction(
                                    height = RefractionHeight(value = 12.dp),
                                    amount = RefractionAmount.Half
                                ),
                                material = GlassMaterial(
                                    brush = SolidColor(value = Color(color = 0xff434056)),
                                    alpha = 0.15f
                                ),
                            ),
                        )
                        .padding(all = padding),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    pages.forEachIndexed { index, page ->
                        key(page) {
                            val itemBackgroundAlpha by animateFloatAsState(
                                targetValue = if (selectedIndexState.value == index && !isDragging) {
                                    0.8f
                                } else {
                                    0f
                                }, animationSpec = spring(
                                    dampingRatio = 0.8f,
                                    stiffness = 200f,
                                )
                            )
                            val itemContentColor by animateColorAsState(
                                targetValue = if (selectedIndexState.value == index && !isDragging) {
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                } else {
                                    Color(color = 0xff8E8E9E)
                                }, animationSpec = spring(
                                    dampingRatio = 0.8f, stiffness = 200f
                                )
                            )
                            val itemContainerColor =
                                MaterialTheme.colorScheme.primaryContainer
                            Column(
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(percent = 50))
                                    .drawBehind {
                                        drawRect(
                                            color = itemContainerColor,
                                            alpha = itemBackgroundAlpha,
                                        )
                                    }
                                    .pointerInput(key1 = Unit) {
                                        detectTapGestures {
                                            if (selectedIndexState.value != index) {
                                                selectedIndexState.value = index
                                                onTabSelected(index)
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
                                    .weight(weight = 1f)
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
                                    tint = itemContentColor,
                                )
                                Text(
                                    text = page.label,
                                    color = itemContentColor,
                                )
                            }
                        }
                    }
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


                if (!useMaterial) Spacer(
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
//                        .background(
//                            color = MaterialTheme.colorScheme.primaryContainer.copy(
//                                alpha = 0.15f
//                            ),
//                            shape = RoundedCornerShape(percent = 50),
//                        )
                        .background(
                            color = background,
                            shape = RoundedCornerShape(percent = 50),
                        )
                        .liquidGlass(
                            state = bottomTabsLiquidGlassProviderState,
                            style = GlassStyle(
                                shape = RoundedCornerShape(percent = 50),
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
                            onDragStarted = { isDragging = true },
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

                                if (selectedIndexState.value != targetIndex) {
                                    selectedIndexState.value = targetIndex
                                    onTabSelected(targetIndex)
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

}

@Preview
@Composable
private fun TKNavBarLiquidGlassPreview() {
    val targetPage = remember {
        mutableIntStateOf(value = 0)
    }
    TrebleKitTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Background)
        ) {
            TKNavBar(
                background = Background,
                useMaterial = false,
                pages = PageList,
                selectedIndexState = targetPage,
                onTabSelected = { index ->
                    targetPage.intValue = index
                },
            )
        }
    }
}

@Preview
@Composable
private fun TKNavBarMaterialPreview() {
    val targetPage = remember {
        mutableIntStateOf(value = 0)
    }
    TrebleKitTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Background)
        ) {
            TKNavBar(
                background = Background,
                useMaterial = true,
                pages = PageList,
                selectedIndexState = targetPage,
                onTabSelected = { index ->
                    targetPage.intValue = index
                },
            )
        }
    }
}