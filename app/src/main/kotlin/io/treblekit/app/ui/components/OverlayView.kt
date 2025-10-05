package io.treblekit.app.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun OverlayView(modifier: Modifier = Modifier) {
    ViewFactory(
        modifier = modifier.fillMaxSize()
    ) {
        getOverlayView
    }
}

@Preview
@Composable
fun OverlayViewPreview() {
    TrebleKitTheme {
        OverlayView()
    }
}