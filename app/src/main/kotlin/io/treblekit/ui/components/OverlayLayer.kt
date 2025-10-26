package io.treblekit.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.theme.capsuleEdgePadding
import io.treblekit.ui.theme.capsuleHeight

@Composable
fun OverlayLayer(
    modifier: Modifier = Modifier,
    backdrop: LayerBackdrop = rememberLayerBackdrop(),
    content: @Composable BoxScope.() -> Unit = {},
) {
    val inspection: Boolean = LocalInspectionMode.current
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        content()
        if (!inspection) ViewFactory(
            modifier = Modifier.fillMaxSize()
        ) {
            getOverlayView
        }
        if (inspection) CapsuleButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    vertical = (TopAppBarDefaults.TopAppBarExpandedHeight - capsuleHeight) / 2,
                    horizontal = capsuleEdgePadding,
                ),
            backdrop = backdrop
        )
    }
}

@Preview
@Composable
private fun OverlayLayerPreview() {
    TrebleKitTheme {
        OverlayLayer()
    }
}