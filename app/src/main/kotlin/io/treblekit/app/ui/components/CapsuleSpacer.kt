package io.treblekit.app.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.treblekit.app.ui.theme.TrebleKitTheme
import io.treblekit.app.ui.theme.topBarPaddingExcess
import io.treblekit.app.ui.utils.rememberCapsulePadding

@Composable
fun CapsuleSpacer(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier.padding(
            paddingValues = rememberCapsulePadding(
                excess = topBarPaddingExcess,
            ),
        ),
    )
}

@Preview
@Composable
fun CapsuleSpacerPreview() {
    TrebleKitTheme {
        CapsuleSpacer()
    }
}