package io.treblekit.app.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.treblekit.app.ui.theme.TrebleKitTheme
import io.treblekit.app.ui.theme.appBackground

@Composable
fun Background(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = backgroundColor,
    ) {
        Spacer(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun BackgroundPreview() {
    TrebleKitTheme {
        Background(backgroundColor = appBackground)
    }
}