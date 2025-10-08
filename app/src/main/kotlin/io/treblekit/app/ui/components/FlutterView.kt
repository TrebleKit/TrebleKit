package io.treblekit.app.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun FlutterView(modifier: Modifier = Modifier) {
    ViewFactory(
        modifier = modifier.fillMaxSize(),
    ) {
        getWrapperView
    }
}

@Preview(showBackground = true)
@Composable
private fun FlutterViewPreview() {
    TrebleKitTheme {
        FlutterView()
    }
}