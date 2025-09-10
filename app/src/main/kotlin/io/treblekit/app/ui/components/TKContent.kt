package io.treblekit.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyant.capsule.ContinuousRoundedRectangle
import io.treblekit.app.ui.theme.AppBackgroundColor
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun TKContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        shape = ContinuousRoundedRectangle(size = 16.dp),
        shadowElevation = 4.dp,
        content = content
    )
}

@Preview
@Composable
private fun TKContentPreview() {
    TrebleKitTheme {
        TKContent(modifier = Modifier.background(color = AppBackgroundColor)) {}
    }
}