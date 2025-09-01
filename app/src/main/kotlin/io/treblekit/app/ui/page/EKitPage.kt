package io.treblekit.app.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.treblekit.app.ui.theme.TrebleKitTheme

@Composable
fun EKitPage(
    modifier: Modifier = Modifier,
    inner: PaddingValues = PaddingValues(),
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(paddingValues = inner),
            text = "EKitPage",
            color = Color.White,
        )
    }
}

@Preview
@Composable
private fun EKitPagePreview() {
    TrebleKitTheme {
        EKitPage()
    }
}