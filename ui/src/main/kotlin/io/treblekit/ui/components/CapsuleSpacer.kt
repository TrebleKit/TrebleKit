package io.treblekit.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.treblekit.ui.theme.TrebleKitTheme
import io.treblekit.ui.theme.topBarPaddingExcess
import io.treblekit.ui.utils.rememberCapsulePadding

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
private fun CapsuleSpacerPreview() {
    TrebleKitTheme {
        CapsuleSpacer()
    }
}