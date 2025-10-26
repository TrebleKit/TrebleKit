package io.treblekit.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import com.kyant.capsule.ContinuousRoundedRectangle
import io.treblekit.ui.navigation.appDestination
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.utils.isCurrentDestination
import io.treblekit.ui.utils.navigateToRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBlock(
    modifier: Modifier = Modifier,
    pageState: PagerState? = null,
    backdrop: LayerBackdrop = rememberLayerBackdrop(),
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val primaryContainerTint: Color = MaterialTheme.colorScheme.primaryContainer
    Box(
        modifier = modifier
            .wrapContentSize()
            .sizeIn(minWidth = 80.dp)
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
                        depthEffect = false,
                    )
                },
                onDrawSurface = {
                    drawRect(
                        color = primaryContainerTint.copy(alpha = 0.8f),
                        blendMode = BlendMode.Hue,
                    )
                },
            ),
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .defaultMinSize(minHeight = 56.dp),
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
                val tooltipState = rememberTooltipState()
                TooltipBox(
                    modifier = Modifier.wrapContentSize(),
                    positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                        positioning = TooltipAnchorPosition.Above,
                        spacingBetweenTooltipAndAnchor = 12.dp
                    ),
                    tooltip = {
                        PlainTooltip {
                            Text(
                                text = stringResource(id = page.label),
                                modifier = Modifier.padding(all = 4.dp),
                            )
                        }
                    },
                    state = tooltipState,
                ) {
                    IconButton(
                        onClick = {
                            if (!isCurrent) coroutineScope.launch {
                                pageState.navigateToRoute(route = page.route)
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
                            contentDescription = stringResource(id = page.label),
                            modifier = Modifier.wrapContentSize(),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun NavBlockPreview() {
    TrebleKitTheme {
        NavBlock()
    }
}