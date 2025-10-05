package io.treblekit.app.ui.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun EffectView(modifier: Modifier = Modifier) {
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

@Preview
@Composable
fun EffectViewPreview() {
    TrebleKitTheme {
        EffectView()
    }
}