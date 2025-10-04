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
import io.treblekit.app.ui.theme.AppBackground
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun EffectBackground(content: @Composable BoxScope.() -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppBackground),
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
        Box(
            modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}

@Preview
@Composable
fun EffectBackgroundPreview() {
    TrebleKitTheme {
        EffectBackground {}
    }
}