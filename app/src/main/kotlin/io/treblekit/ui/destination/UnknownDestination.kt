package io.treblekit.ui.destination

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.treblekit.ui.theme.TrebleKitTheme

@Composable
fun UnknownDestination(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "未知页面",
            color = MaterialTheme.colorScheme.error,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
        )
    }
}

@Preview
@Composable
fun UnknownDestinationPreview() {
    TrebleKitTheme {
        UnknownDestination()
    }
}