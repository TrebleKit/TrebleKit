package io.treblekit.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import com.kyant.capsule.ContinuousCapsule
import io.treblekit.app.ui.theme.TrebleKitTheme
import io.treblekit.app.ui.theme.capsuleContainer
import io.treblekit.app.ui.theme.capsuleEdgePadding
import io.treblekit.app.ui.theme.capsuleHeight
import io.treblekit.app.ui.theme.capsuleIndent
import io.treblekit.app.ui.theme.capsuleWidth
import io.treblekit.app.ui.utils.NoOnClick

@Composable
fun OverlayLayer(
    modifier: Modifier = Modifier,
    backdrop: Backdrop = rememberLayerBackdrop(),
    content: @Composable BoxScope.() -> Unit = {},
) {
    val inspection: Boolean = LocalInspectionMode.current
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        content()
        OverlayView()
        AnimatedVisibility(
            visible = inspection,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Box(
                modifier = Modifier
                    .padding(
                        vertical = (TopAppBarDefaults.TopAppBarExpandedHeight - capsuleHeight) / 2,
                        horizontal = capsuleEdgePadding,
                    )
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
                                depthEffect = false,
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
                            tint = Color.White,
                        )
                    }
                    VerticalDivider(
                        modifier = Modifier
                            .padding(vertical = capsuleIndent)
                            .wrapContentWidth()
                            .fillMaxHeight(),
                        color = Color.White,
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
                            tint = Color.White,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun OverlayLayerPreview() {
    TrebleKitTheme {
        OverlayLayer()
    }
}

@Composable
private fun OverlayView(modifier: Modifier = Modifier) {
    ViewFactory(
        modifier = modifier.fillMaxSize()
    ) {
        getOverlayView
    }
}

@Preview(showBackground = true)
@Composable
private fun OverlayViewPreview() {
    TrebleKitTheme {
        OverlayView()
    }
}