package io.treblekit.app.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.treblekit.app.ui.theme.Background
import io.treblekit.app.ui.theme.TrebleKitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EbKitPage(
    modifier: Modifier = Modifier,
    inner: PaddingValues = PaddingValues(),
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues = inner)
            .padding(all = 16.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "EbKit")
                    },
                    windowInsets = WindowInsets(),
                )
            },
            contentWindowInsets = WindowInsets(),
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
            ) {

            }
        }
    }
}

@Preview
@Composable
private fun EbKitPagePreview() {
    TrebleKitTheme {
        EbKitPage(
            modifier = Modifier.background(
                color = Background,
            ),
        )
    }
}