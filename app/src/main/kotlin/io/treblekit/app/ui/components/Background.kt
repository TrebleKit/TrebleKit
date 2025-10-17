package io.treblekit.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberCombinedBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import io.treblekit.app.ui.theme.TrebleKitTheme
import io.treblekit.app.ui.theme.appBackground

@Composable
fun Background(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.(Backdrop) -> Unit = {},
) {
    val backgroundBackdrop: LayerBackdrop = rememberLayerBackdrop()
    val effectBackdrop: LayerBackdrop = rememberLayerBackdrop()
    val backdrop: Backdrop = rememberCombinedBackdrop(
        backdrop1 = backgroundBackdrop,
        backdrop2 = effectBackdrop,
    )
    Box(
        modifier = modifier.fillMaxSize()
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
        content(backdrop)
    }
}

@Preview
@Composable
private fun BackgroundPreview() {
    TrebleKitTheme {
        Background()
    }
}

@Composable
private fun EffectView(modifier: Modifier = Modifier) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
    ) {
        ViewFactory(
            modifier = Modifier
                .fillMaxSize()
                .offset(
                    y = with(
                        receiver = LocalDensity.current,
                    ) {
                        return@with (constraints.maxHeight / 4).toDp()
                    }
                ),
        ) {
            getEffectView
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EffectViewPreview() {
    TrebleKitTheme {
        EffectView()
    }
}