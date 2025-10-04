package io.treblekit.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.treblekit.app.ui.theme.AppBackground
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun EffectBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppBackground),
    ) {
        ViewFactory(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 250.dp),
        ) { getEffectView }
        content()
    }
}

@Preview
@Composable
fun EffectBackgroundPreview() {
    TrebleKitTheme {
        EffectBackground {}
    }
}