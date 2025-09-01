package io.treblekit.app.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    inner: PaddingValues = PaddingValues(),
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues = inner)
    )
}

@Preview
@Composable
private fun HomePagePreview() {
    TrebleKitTheme {
        HomePage()
    }
}