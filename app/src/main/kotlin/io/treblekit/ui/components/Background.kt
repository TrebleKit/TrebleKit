package io.treblekit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.theme.appBackground

@Composable
fun Background(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.(LayerBackdrop) -> Unit = {},
) {
    val backdrop: LayerBackdrop = rememberLayerBackdrop()
    val density: Density = LocalDensity.current
    val inspection: Boolean = LocalInspectionMode.current
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        BoxWithConstraints(
            modifier = modifier
                .fillMaxSize()
                .background(color = appBackground)
                .layerBackdrop(backdrop = backdrop),
        ) {
            if (!inspection) ViewFactory(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(
                        y = with(receiver = density) {
                            return@with (constraints.maxHeight / 7 * 2).toDp()
                        }
                    ),
            ) {
                getEffectView
            }
        }
        content.invoke(this@Box, backdrop)
    }
}

@Preview
@Composable
private fun BackgroundPreview() {
    TrebleKitTheme {
        Background()
    }
}