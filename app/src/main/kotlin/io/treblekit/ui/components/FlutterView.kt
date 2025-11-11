package io.treblekit.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.treblekit.ui.theme.TrebleKitTheme

@Composable
fun  FlutterView(modifier: Modifier = Modifier) {
    ViewFactory(
        modifier = modifier.fillMaxSize(),
    ) {
        wrapperView
    }
}

@Preview(showBackground = true)
@Composable
private fun FlutterViewPreview() {
    TrebleKitTheme {
        FlutterView()
    }
}